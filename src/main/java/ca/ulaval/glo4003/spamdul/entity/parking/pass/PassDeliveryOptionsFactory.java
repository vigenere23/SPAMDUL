package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.pass.DeliveryDto;

public class PassDeliveryOptionsFactory {

  public DeliveryOptions create(DeliveryDto deliveryDto, String subject) {
    DeliveryOptions deliveryOptions = new DeliveryOptions();

    if (deliveryDto.deliveryMode == DeliveryMode.POST) {
      deliveryOptions.postalAddress = deliveryDto.postalAddress;
    } else if (deliveryDto.deliveryMode == DeliveryMode.EMAIL){
      deliveryOptions.emailAddress = deliveryDto.emailAddress;
    } else if (deliveryDto.deliveryMode == DeliveryMode.SSP_OFFICE) {
      //Nothing to do
    } else {
      throw new InvalidDeliveryModeException("Invalid delivery mode");
    }

    deliveryOptions.subject = subject;

    return deliveryOptions;
  }
}
