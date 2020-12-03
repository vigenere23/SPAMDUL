package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointNotDisconnectedException extends ChargingPointException {

  public ChargingPointNotDisconnectedException() {
    super("the charging point must be disconnected first");
  }
}
