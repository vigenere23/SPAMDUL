package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointNotFoundException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_NOT_FOUND";
  }

  public String getDescription() {
    return "This charging point does not exist";
  }
}
