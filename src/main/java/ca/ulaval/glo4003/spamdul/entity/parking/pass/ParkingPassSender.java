package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.DeliveryDto;

public class ParkingPassSender {

  private final ParkingPassDeliveryOptionsFactory parkingPassDeliveryOptionsFactory;
  private final DeliveryStrategyFactory deliveryStrategyFactory;

  public ParkingPassSender(ParkingPassDeliveryOptionsFactory parkingPassDeliveryOptionsFactory,
                           DeliveryStrategyFactory deliveryStrategyFactory) {
    this.parkingPassDeliveryOptionsFactory = parkingPassDeliveryOptionsFactory;
    this.deliveryStrategyFactory = deliveryStrategyFactory;
  }

  public void sendPass(DeliveryDto deliveryDto, ParkingPassCode parkingPassCode) {
    String CONTENT = "Your pass code is: %s";
    String SUBJECT = "Your new pass code";
    String content = String.format(CONTENT, parkingPassCode.toString());

    DeliveryOptions deliveryOptions = parkingPassDeliveryOptionsFactory.create(deliveryDto, SUBJECT);
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(deliveryDto.deliveryMode);

    deliveryStrategy.deliver(deliveryOptions, content);
  }
}
