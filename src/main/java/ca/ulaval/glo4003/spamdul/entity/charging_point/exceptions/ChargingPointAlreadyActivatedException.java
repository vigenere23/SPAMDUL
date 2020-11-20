package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointAlreadyActivatedException extends ChargingPointException {

  public ChargingPointAlreadyActivatedException() {
    super("The charging point is already activated");
  }
}
