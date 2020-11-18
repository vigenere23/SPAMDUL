package ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.dto.ChargingPointActivationRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.dto.ChargingPointResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.dto.ChargingPointsResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingUseCase;
import java.util.List;
import javax.ws.rs.core.Response;

public class ChargingPointResourceImpl implements ChargingPointResource {

  private final ChargingUseCase chargingUseCase;
  private final ChargingPointAssembler chargingPointAssembler;

  public ChargingPointResourceImpl(ChargingUseCase chargingUseCase,
                                   ChargingPointAssembler chargingPointAssembler) {
    this.chargingUseCase = chargingUseCase;
    this.chargingPointAssembler = chargingPointAssembler;
  }

  @Override public Response getAll() {
    List<ChargingPoint> chargingPoints = chargingUseCase.getAll();
    ChargingPointsResponse response = chargingPointAssembler.toResponse(chargingPoints);
    return Response.ok(response).build();
  }

  @Override public Response activateCharging(String chargingPointIdString, ChargingPointActivationRequest request) {
    String rechargULCardIdString = request == null ? null : request.rechargULCardId;
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    ChargingPoint chargingPoint = chargingUseCase.activateChargingPoint(chargingPointId, rechargULCardId);

    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);
    return Response.ok(response).build();
  }

  @Override public Response startCharging(String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);

    ChargingPoint chargingPoint = chargingUseCase.startRecharging(chargingPointId);

    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);
    return Response.ok(response).build();
  }

  @Override public Response stopCharging(String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);

    ChargingPoint chargingPoint = chargingUseCase.stopRecharging(chargingPointId);

    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);
    return Response.ok(response).build();
  }
}
