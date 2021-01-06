package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.CarCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation.PermitCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.permit.PermitCreationDto;

public class PermitCreationInfosAssembler {

  private final CarCreationInfosAssembler carCreationInfosAssembler;

  public PermitCreationInfosAssembler(CarCreationInfosAssembler carCreationInfosAssembler) {
    this.carCreationInfosAssembler = carCreationInfosAssembler;
  }

  public PermitCreationInfos fromDto(PermitCreationDto dto) {
    CarCreationInfos car = carCreationInfosAssembler.fromDto(dto.car);
    return new PermitCreationInfos(car);
  }
}
