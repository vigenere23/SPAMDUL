package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointAlreadyExistsException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_ALREADY_EXISTS";
  }

  public String getDescription() {
    return "This charging point already exists";
  }
}
