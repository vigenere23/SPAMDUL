package ca.ulaval.glo4003.spamdul.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.assemblers.delivery.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.parking.api.pass.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass.DeliveryDto;

public class DeliveryAssembler {

  private final EmailAddressAssembler emailAddressAssembler;
  private final PostalAddressAssembler postalAddressAssembler;

  public DeliveryAssembler(EmailAddressAssembler emailAddressAssembler, PostalAddressAssembler postalAddressAssembler) {
    this.emailAddressAssembler = emailAddressAssembler;
    this.postalAddressAssembler = postalAddressAssembler;
  }

  public DeliveryDto fromRequest(DeliveryRequest deliveryRequest) {
    DeliveryDto deliveryDto = new DeliveryDto();
    DeliveryMode deliveryMode = getDeliveryMode(deliveryRequest.deliveryMode);
    deliveryDto.deliveryMode = deliveryMode;

    if (deliveryMode == DeliveryMode.POST) {
      deliveryDto.postalAddress = postalAddressAssembler.fromDto(deliveryRequest.postalAddress);
    } else if (deliveryMode == DeliveryMode.EMAIL) {
      deliveryDto.emailAddress = emailAddressAssembler.fromString(deliveryRequest.emailAddress);
    }

    return deliveryDto;
  }

  private DeliveryMode getDeliveryMode(String deliveryMode) {
    try {
      return DeliveryMode.valueOf(deliveryMode.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidDeliveryModeException();
    }
  }
}
