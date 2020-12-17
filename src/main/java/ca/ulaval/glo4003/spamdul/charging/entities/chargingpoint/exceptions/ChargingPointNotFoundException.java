package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions;

public class ChargingPointNotFoundException extends ChargingPointException {

  @Override public String getError() {
    return "CHARGING_POINT_NOT_FOUND";
  }

  @Override public String getDescription() {
    return "This charging point does not exist";
  }
}
