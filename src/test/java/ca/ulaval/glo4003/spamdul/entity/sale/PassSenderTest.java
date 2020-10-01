package ca.ulaval.glo4003.spamdul.entity.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PassSenderTest {
    private static final PassCode A_PASS_CODE = new PassCode();
    private static final DeliveryMode A_DELIVERY_MODE = DeliveryMode.POST;
    private static final String CONTENT = "Your pass code is: %s";
    private static final String SUBJECT = "Your new pass code";

    private DeliveryDto deliveryDto;
    private DeliveryOptions deliveryOptions;

    @Mock
    private PassDeliveryOptionsFactory passDeliveryOptionsFactory;
    @Mock
    private DeliveryStrategyFactory deliveryStrategyFactory;
    @Mock
    private DeliveryStrategy deliveryStrategy;

    private PassSender passSender ;

    @Before
    public void setUp() {
        deliveryOptions = new DeliveryOptions();
        deliveryDto = new DeliveryDto();
        deliveryDto.deliveryMode = A_DELIVERY_MODE;
        passSender = new PassSender(passDeliveryOptionsFactory, deliveryStrategyFactory);
        when(passDeliveryOptionsFactory.create(deliveryDto, SUBJECT)).thenReturn(deliveryOptions);
        when(deliveryStrategyFactory.create(A_DELIVERY_MODE)).thenReturn(deliveryStrategy);
    }

    @Test
    public void whenSendingPass_shouldCallDeliveryBridgeFactoryToCreateDeliveryBridge() {
        passSender.sendPass(deliveryDto, A_PASS_CODE);
        verify(deliveryStrategyFactory).create(A_DELIVERY_MODE);
    }

    @Test
    public void whenSendingPass_shouldCallDeliveryOptionsFactoryToCreateDeliveryOptions() {
        passSender.sendPass(deliveryDto, A_PASS_CODE);
        verify(passDeliveryOptionsFactory).create(deliveryDto, SUBJECT);
    }

    @Test
    public void givenPassCode_whenSendingPass_shouldSendMessageContainingPassCode() {
        passSender.sendPass(deliveryDto, A_PASS_CODE);
        String message = String.format(CONTENT, A_PASS_CODE.toString());
        verify(deliveryStrategy).deliver(deliveryOptions, message);
    }
}
