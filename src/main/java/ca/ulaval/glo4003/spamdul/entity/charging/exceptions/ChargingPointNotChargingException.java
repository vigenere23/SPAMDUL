package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointNotChargingException extends ChargingPointException {

  public ChargingPointNotChargingException() {
    super("The charging point must be charging first");
  }
}
