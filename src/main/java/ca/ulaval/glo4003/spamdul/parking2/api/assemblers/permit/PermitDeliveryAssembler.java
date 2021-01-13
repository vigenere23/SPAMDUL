package ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.permit.PermitDeliveryRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.delivery.DeliveryType;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitDeliveryDto;

public class PermitDeliveryAssembler {

  public PermitDeliveryDto fromRequest(PermitDeliveryRequest request) {
    PermitDeliveryDto dto = new PermitDeliveryDto();
    dto.type = DeliveryType.valueOf(request.type.toUpperCase());
    dto.emailAddress = request.emailAddress;
    dto.addressNumber = request.addressNumber;
    dto.street = request.street;
    dto.apartmentNumber = request.apartmentNumber;
    dto.city = request.city;
    dto.region = request.region;
    dto.country = request.country;
    dto.postalCode = request.postalCode;

    return dto;
  }
}
