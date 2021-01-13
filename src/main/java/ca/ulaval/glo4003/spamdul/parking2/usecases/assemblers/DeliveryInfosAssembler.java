package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.delivery.DeliveryInfos;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitDeliveryDto;

public class DeliveryInfosAssembler {

  public DeliveryInfos fromDto(PermitDeliveryDto dto) {
    return new DeliveryInfos(dto.emailAddress,
                             dto.addressNumber,
                             dto.street,
                             dto.apartmentNumber,
                             dto.city,
                             dto.country,
                             dto.region,
                             dto.postalCode);
  }
}
