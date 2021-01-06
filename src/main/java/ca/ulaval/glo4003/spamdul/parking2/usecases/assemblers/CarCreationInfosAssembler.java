package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.CarCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.permit.CarCreationDto;

public class CarCreationInfosAssembler {

  public CarCreationInfos fromDto(CarCreationDto dto) {
    if (dto != null) {
      return new CarCreationInfos(dto.brand, dto.model, dto.year, dto.licensePlate, dto.type);
    } else {
      return null;
    }
  }
}
