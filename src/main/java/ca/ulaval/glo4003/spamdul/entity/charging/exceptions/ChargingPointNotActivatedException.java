package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointNotActivatedException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_NOT_ACTIVATED";
  }

  public String getDescription() {
    return "The charging point must be activated first";
  }
}
