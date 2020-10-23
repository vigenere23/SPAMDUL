package ca.ulaval.glo4003.spamdul.entity.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategyFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.usecases.pass.DeliveryDto;

public class PassSender {

  private final PassDeliveryOptionsFactory passDeliveryOptionsFactory;
  private final DeliveryStrategyFactory deliveryStrategyFactory;

  public PassSender(PassDeliveryOptionsFactory passDeliveryOptionsFactory,
                    DeliveryStrategyFactory deliveryStrategyFactory) {
    this.passDeliveryOptionsFactory = passDeliveryOptionsFactory;
    this.deliveryStrategyFactory = deliveryStrategyFactory;
  }

  public void sendPass(DeliveryDto deliveryDto, PassCode passCode) {
    DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(deliveryDto.deliveryMode);
    String CONTENT = "Your pass code is: %s";
    String SUBJECT = "Your new pass code";
    String content = String.format(CONTENT, passCode.toString());
    DeliveryOptions deliveryOptions = passDeliveryOptionsFactory.create(deliveryDto, SUBJECT);
    deliveryStrategy.deliver(deliveryOptions, content);
  }
}
