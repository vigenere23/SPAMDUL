package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointNotActivatedException extends ChargingPointException {

  public ChargingPointNotActivatedException() {
    super("The charging point must be activated first");
  }
}
