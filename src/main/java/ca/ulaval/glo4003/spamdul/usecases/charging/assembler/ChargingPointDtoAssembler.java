package ca.ulaval.glo4003.spamdul.usecases.charging.assembler;

import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPoint;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.ChargingPointDto;

public class ChargingPointDtoAssembler {

  public ChargingPointDto toDto(ChargingPoint chargingPoint) {
    ChargingPointDto dto = new ChargingPointDto();
    dto.id = chargingPoint.getId();
    dto.state = chargingPoint.getState();

    return dto;
  }

}
