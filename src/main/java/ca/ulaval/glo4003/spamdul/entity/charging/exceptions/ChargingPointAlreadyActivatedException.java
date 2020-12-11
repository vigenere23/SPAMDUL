package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointAlreadyActivatedException extends ChargingPointException {

  public ChargingPointAlreadyActivatedException() {
    super("The charging point is already activated");
  }
}
