package ca.ulaval.glo4003.spamdul.api.charging;

import ca.ulaval.glo4003.spamdul.api.charging.dto.ChargingPointActivationRequest;
import ca.ulaval.glo4003.spamdul.api.charging.dto.ChargingPointResponse;
import ca.ulaval.glo4003.spamdul.api.charging.dto.ChargingPointsResponse;
import ca.ulaval.glo4003.spamdul.assemblers.charging.ChargingPointAssembler;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingPointUseCase;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.ChargingPointDto;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/charging-points")
public class ChargingPointResource {

  private final ChargingPointUseCase chargingPointUseCase;
  private final ChargingPointAssembler chargingPointAssembler;

  public ChargingPointResource(ChargingPointUseCase chargingPointUseCase,
                               ChargingPointAssembler chargingPointAssembler) {
    this.chargingPointUseCase = chargingPointUseCase;
    this.chargingPointAssembler = chargingPointAssembler;
  }

  @GET
  public Response getAll() {
    List<ChargingPointDto> chargingPoints = chargingPointUseCase.getAllChargingPoints();
    ChargingPointsResponse response = chargingPointAssembler.toResponse(chargingPoints);

    return Response.ok(response).build();
  }

  @Path("/{id}")
  @GET
  public Response getSingle(@PathParam("id") String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);

    ChargingPointDto chargingPoint = chargingPointUseCase.getChargingPoint(chargingPointId);
    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);

    return Response.ok(response).build();
  }

  @Path("/{id}/activate")
  @POST
  @Consumes("application/json")
  public Response activateCharging(@PathParam("id") String chargingPointIdString,
                                   ChargingPointActivationRequest request) {
    String rechargULCardIdString = request == null ? null : request.rechargULCardId;
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    ChargingPointDto chargingPoint = chargingPointUseCase.activateChargingPoint(chargingPointId, rechargULCardId);
    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);

    return Response.ok(response).build();
  }

  @Path("/{id}/connect")
  @POST
  public Response startCharging(@PathParam("id") String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);

    ChargingPointDto chargingPoint = chargingPointUseCase.startRecharging(chargingPointId);
    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);

    return Response.ok(response).build();
  }

  @Path("/{id}/disconnect")
  @POST
  public Response stopCharging(@PathParam("id") String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);

    ChargingPointDto chargingPoint = chargingPointUseCase.stopRecharging(chargingPointId);
    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);

    return Response.ok(response).build();
  }

  @Path("/{id}/deactivate")
  @POST
  public Response deactivateCharging(@PathParam("id") String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);

    ChargingPointDto chargingPoint = chargingPointUseCase.deactivateChargingPoint(chargingPointId);
    ChargingPointResponse response = chargingPointAssembler.toResponse(chargingPoint);

    return Response.ok(response).build();
  }
}
