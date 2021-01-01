package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions;

public class ChargingPointAlreadyActivatedException extends ChargingPointException {

  @Override public String getError() {
    return "CHARGING_POINT_ALREADY_ACTIVE";
  }

  @Override public String getDescription() {
    return "The charging point is already activated";
  }
}
