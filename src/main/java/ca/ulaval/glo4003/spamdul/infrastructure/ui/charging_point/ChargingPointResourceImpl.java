package ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point;

import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingPointService;
import javax.ws.rs.core.Response;

public class ChargingPointResourceImpl implements
    ChargingPointResource {

  private final ChargingPointService chargingPointService;

  public ChargingPointResourceImpl(ChargingPointService chargingPointService) {
    this.chargingPointService = chargingPointService;
  }

  @Override public Response getAll() {
    return null;
  }

  @Override public Response activateCharging(String chargingPointId) {
    return null;
  }

  @Override public Response startCharging(String chargingPointId) {
    return null;
  }

  @Override public Response stopCharging(String chargingPointId) {
    return null;
  }
}
