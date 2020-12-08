package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointAlreadyChargingException extends ChargingPointException {

  public ChargingPointAlreadyChargingException() {
    super("The charging point is already charging");
  }
}
