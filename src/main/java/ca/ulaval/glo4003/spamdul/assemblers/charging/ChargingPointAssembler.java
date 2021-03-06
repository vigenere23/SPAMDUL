package ca.ulaval.glo4003.spamdul.assemblers.charging;

import ca.ulaval.glo4003.spamdul.charging.api.chargingpoint.dto.ChargingPointResponse;
import ca.ulaval.glo4003.spamdul.charging.api.chargingpoint.dto.ChargingPointsResponse;
import ca.ulaval.glo4003.spamdul.charging.usecases.dto.ChargingPointDto;
import java.util.List;
import java.util.stream.Collectors;

public class ChargingPointAssembler {

  public ChargingPointResponse toResponse(ChargingPointDto chargingPointDto) {
    ChargingPointResponse response = new ChargingPointResponse();
    response.id = chargingPointDto.id.toString();
    response.state = chargingPointDto.state;

    return response;
  }

  public ChargingPointsResponse toResponse(List<ChargingPointDto> chargingPoints) {
    ChargingPointsResponse response = new ChargingPointsResponse();
    response.chargingPoints = chargingPoints.stream().map(this::toResponse).collect(Collectors.toList());

    return response;
  }
}
