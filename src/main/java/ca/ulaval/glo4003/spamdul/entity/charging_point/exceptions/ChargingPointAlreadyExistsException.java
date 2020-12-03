package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointAlreadyExistsException extends ChargingPointException {

  public ChargingPointAlreadyExistsException() {
    super("this charging point already exists");
  }
}
