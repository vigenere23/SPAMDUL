package ca.ulaval.glo4003.spamdul.entity.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryBridge;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryBridgeFactory;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.usecases.sale.DeliveryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PassSenderTest {
    private static final UserId A_USER_ID = new UserId();
    private static final PassCode A_PASS_CODE = new PassCode();
    private static final DeliveryMode A_DELIVERY_MODE = DeliveryMode.POST;
    private static final String A_NAME = "fglm";
    private static final Gender A_GENDER = Gender.OTHER;
    private static final LocalDate A_DATE = LocalDate.of(1111, 11, 11);
    String CONTENT = "Your pass code is: %s";
    String SUBJECT = "Your new pass code";

    private DeliveryDto deliveryDto;
    private User user;
    private DeliveryOptions deliveryOptions;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PassDeliveryOptionsFactory passDeliveryOptionsFactory;
    @Mock
    private DeliveryBridgeFactory deliveryBridgeFactory;
    @Mock
    private DeliveryBridge deliveryBridge;

    private PassSender passSender ;



    @Before
    public void setUp() {
        user = new User(A_USER_ID, A_NAME, A_GENDER, A_DATE);
        deliveryOptions = new DeliveryOptions();
        deliveryDto = new DeliveryDto();
        deliveryDto.deliveryMode = A_DELIVERY_MODE;
        passSender = new PassSender(userRepository, passDeliveryOptionsFactory, deliveryBridgeFactory);
        when(userRepository.findById(A_USER_ID)).thenReturn(user);;
        when(passDeliveryOptionsFactory.create(deliveryDto, SUBJECT, A_NAME)).thenReturn(deliveryOptions);
        when(deliveryBridgeFactory.create(A_DELIVERY_MODE)).thenReturn(deliveryBridge);
    }

    @Test
    public void whenSendingPass_shouldCallDeliveryBridgeFactoryToCreateDeliveryBridge() {
        passSender.sendPass(A_USER_ID, deliveryDto, A_PASS_CODE);
        verify(deliveryBridgeFactory).create(A_DELIVERY_MODE);
    }

    @Test
    public void whenSendingPass_shouldFindUserInUserRepository() {
        passSender.sendPass(A_USER_ID, deliveryDto, A_PASS_CODE);
        verify(userRepository).findById(A_USER_ID);
    }

    @Test
    public void whenSendingPass_shouldCallDeliveryOptionsFactoryToCreateDeliveryOptions() {
        passSender.sendPass(A_USER_ID, deliveryDto, A_PASS_CODE);
        verify(passDeliveryOptionsFactory).create(deliveryDto, SUBJECT, A_NAME);
    }

    @Test
    public void givenPassCode_whenSendingPass_shouldSendMessageContainingPassCode() {
        passSender.sendPass(A_USER_ID, deliveryDto, A_PASS_CODE);
        String message = String.format(CONTENT, A_PASS_CODE.toString());
        verify(deliveryBridge).send(deliveryOptions, message);
    }
}
