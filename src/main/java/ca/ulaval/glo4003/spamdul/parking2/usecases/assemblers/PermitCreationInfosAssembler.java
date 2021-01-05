package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;

public class PermitCreationInfosAssembler {

  public PermitCreationInfos fromDto(PermitCreationDto dto) {
    return new PermitCreationInfos(dto.carBrand,
                                   dto.carModel,
                                   dto.carYear,
                                   dto.licensePlate,
                                   dto.carType);
  }
}
