package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointAlreadyActivatedException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_ALREADY_ACTIVE";
  }

  public String getDescription() {
    return "The charging point is already activated";
  }
}
