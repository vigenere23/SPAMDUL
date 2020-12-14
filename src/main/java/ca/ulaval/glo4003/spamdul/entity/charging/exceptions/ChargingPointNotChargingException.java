package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointNotChargingException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_NOT_CHARGING";
  }

  public String getDescription() {
    return "The charging point must be charging first";
  }
}
