package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.usecases.pass.DeliveryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingPassSenderTest {

  private static final ParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("123");
  private static final DeliveryMode A_DELIVERY_MODE = DeliveryMode.POST;
  private static final String CONTENT = "Your pass code is: %s";
  private static final String SUBJECT = "Your new pass code";

  private DeliveryDto deliveryDto;
  private DeliveryOptions deliveryOptions;

  @Mock
  private ParkingPassDeliveryOptionsFactory parkingPassDeliveryOptionsFactory;
  @Mock
  private DeliveryStrategyFactory deliveryStrategyFactory;
  @Mock
  private DeliveryStrategy deliveryStrategy;

  private ParkingPassSender parkingPassSender;

  @Before
  public void setUp() {
    deliveryOptions = new DeliveryOptions();
    deliveryDto = new DeliveryDto();
    deliveryDto.deliveryMode = A_DELIVERY_MODE;
    parkingPassSender = new ParkingPassSender(parkingPassDeliveryOptionsFactory, deliveryStrategyFactory);
    when(parkingPassDeliveryOptionsFactory.create(deliveryDto, SUBJECT)).thenReturn(deliveryOptions);
    when(deliveryStrategyFactory.create(A_DELIVERY_MODE)).thenReturn(deliveryStrategy);
  }

  @Test
  public void whenSendingPass_shouldCallDeliveryBridgeFactoryToCreateDeliveryBridge() {
    parkingPassSender.sendPass(deliveryDto, A_PASS_CODE);

    verify(deliveryStrategyFactory).create(A_DELIVERY_MODE);
  }

  @Test
  public void whenSendingPass_shouldCallDeliveryOptionsFactoryToCreateDeliveryOptions() {
    parkingPassSender.sendPass(deliveryDto, A_PASS_CODE);

    verify(parkingPassDeliveryOptionsFactory).create(deliveryDto, SUBJECT);
  }

  @Test
  public void givenPassCode_whenSendingPass_shouldSendMessageContainingPassCode() {
    parkingPassSender.sendPass(deliveryDto, A_PASS_CODE);

    String message = String.format(CONTENT, A_PASS_CODE.toString());
    verify(deliveryStrategy).deliver(deliveryOptions, message);
  }
}
