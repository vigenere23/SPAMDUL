package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointAlreadyChargingException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_ALREADY_CHARGING";
  }

  public String getDescription() {
    return "The charging point is already charging";
  }
}
