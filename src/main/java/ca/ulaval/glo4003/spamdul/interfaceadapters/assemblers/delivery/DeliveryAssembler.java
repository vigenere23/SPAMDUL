package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.DeliveryRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.usecases.pass.DeliveryDto;

public class DeliveryAssembler {

  private EmailAddressAssembler emailAddressAssembler;
  private PostalAddressAssembler postalAddressAssembler;

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
    } else {
      deliveryDto.emailAddress = emailAddressAssembler.fromString(deliveryRequest.emailAddress);
    }

    return deliveryDto;
  }

  private DeliveryMode getDeliveryMode(String deliveryMode) {
    try {
      return DeliveryMode.valueOf(deliveryMode.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidDeliveryModeException("The delivery is either made by post or by mail");
    }
  }
}
