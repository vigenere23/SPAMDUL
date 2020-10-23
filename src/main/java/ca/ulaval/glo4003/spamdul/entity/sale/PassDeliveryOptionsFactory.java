package ca.ulaval.glo4003.spamdul.entity.sale;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.usecases.pass.DeliveryDto;

public class PassDeliveryOptionsFactory {

  public DeliveryOptions create(DeliveryDto deliveryDto, String subject) {
    DeliveryOptions deliveryOptions = new DeliveryOptions();

    if (deliveryDto.deliveryMode == DeliveryMode.POST) {
      deliveryOptions.postalAddress = deliveryDto.postalAddress;
    } else {
      deliveryOptions.emailAddress = deliveryDto.emailAddress;
    }

    deliveryOptions.subject = subject;

    return deliveryOptions;
  }
}
