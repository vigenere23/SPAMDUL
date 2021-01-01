package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions;

public class ChargingPointAlreadyExistsException extends ChargingPointException {

  @Override public String getError() {
    return "CHARGING_POINT_ALREADY_EXISTS";
  }

  @Override public String getDescription() {
    return "This charging point already exists";
  }
}
