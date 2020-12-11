package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointNotDisconnectedException extends ChargingPointException {

  public ChargingPointNotDisconnectedException() {
    super("The charging point must be disconnected first");
  }
}
