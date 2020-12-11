package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointAlreadyExistsException extends ChargingPointException {

  public ChargingPointAlreadyExistsException() {
    super("This charging point already exists");
  }
}
