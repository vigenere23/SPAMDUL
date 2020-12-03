package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointAlreadyExistsException extends ChargingPointException {

  public ChargingPointAlreadyExistsException() {
    super("This charging point already exists");
  }
}
