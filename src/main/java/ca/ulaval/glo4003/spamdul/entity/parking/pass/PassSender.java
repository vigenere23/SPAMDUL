package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.DeliveryDto;

public class PassSender {

  private final PassDeliveryOptionsFactory passDeliveryOptionsFactory;
  private final DeliveryStrategyFactory deliveryStrategyFactory;

  public PassSender(PassDeliveryOptionsFactory passDeliveryOptionsFactory,
                    DeliveryStrategyFactory deliveryStrategyFactory) {
    this.passDeliveryOptionsFactory = passDeliveryOptionsFactory;
    this.deliveryStrategyFactory = deliveryStrategyFactory;
  }

  public void sendPass(DeliveryDto deliveryDto, PassCode passCode) {
    String CONTENT = "Your pass code is: %s";
    String SUBJECT = "Your new pass code";
    String content = String.format(CONTENT, passCode.toString());

    DeliveryOptions deliveryOptions = passDeliveryOptionsFactory.create(deliveryDto, SUBJECT);
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(deliveryDto.deliveryMode);

    deliveryStrategy.deliver(deliveryOptions, content);
  }
}
