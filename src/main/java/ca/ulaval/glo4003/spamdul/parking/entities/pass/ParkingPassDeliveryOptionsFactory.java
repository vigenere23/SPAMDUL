package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass.DeliveryDto;

public class ParkingPassDeliveryOptionsFactory {

  public DeliveryOptions create(DeliveryDto deliveryDto, String subject) {
    DeliveryOptions deliveryOptions = new DeliveryOptions();

    if (deliveryDto.deliveryMode == DeliveryMode.POST) {
      deliveryOptions.postalAddress = deliveryDto.postalAddress;
    } else if (deliveryDto.deliveryMode == DeliveryMode.EMAIL) {
      deliveryOptions.emailAddress = deliveryDto.emailAddress;
    } else if (deliveryDto.deliveryMode == DeliveryMode.SSP_OFFICE) {
      //Nothing to do
    } else {
      throw new InvalidDeliveryModeException();
    }

    deliveryOptions.subject = subject;

    return deliveryOptions;
  }
}
