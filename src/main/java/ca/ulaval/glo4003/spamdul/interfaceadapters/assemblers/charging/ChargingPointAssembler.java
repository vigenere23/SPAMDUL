package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging;

import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPoint;
import ca.ulaval.glo4003.spamdul.ui.charging.dto.ChargingPointResponse;
import ca.ulaval.glo4003.spamdul.ui.charging.dto.ChargingPointsResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ChargingPointAssembler {

  public ChargingPointResponse toResponse(ChargingPoint chargingPoint) {
    ChargingPointResponse response = new ChargingPointResponse();
    response.id = chargingPoint.getId().toString();
    response.state = chargingPoint.getState();

    return response;
  }

  public ChargingPointsResponse toResponse(List<ChargingPoint> chargingPoints) {
    ChargingPointsResponse response = new ChargingPointsResponse();
    response.chargingPoints = chargingPoints.stream().map(this::toResponse).collect(Collectors.toList());

    return response;
  }
}
