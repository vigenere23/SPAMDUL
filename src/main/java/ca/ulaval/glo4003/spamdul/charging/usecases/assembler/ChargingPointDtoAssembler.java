package ca.ulaval.glo4003.spamdul.charging.usecases.assembler;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPoint;
import ca.ulaval.glo4003.spamdul.charging.usecases.dto.ChargingPointDto;

public class ChargingPointDtoAssembler {

  public ChargingPointDto toDto(ChargingPoint chargingPoint) {
    ChargingPointDto dto = new ChargingPointDto();
    dto.id = chargingPoint.getId();
    dto.state = chargingPoint.getState();

    return dto;
  }

}
