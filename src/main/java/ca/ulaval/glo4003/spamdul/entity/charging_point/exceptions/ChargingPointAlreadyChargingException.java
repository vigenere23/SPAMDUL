package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointAlreadyChargingException extends ChargingPointException {

  public ChargingPointAlreadyChargingException() {
    super("the charging point is already charging");
  }
}
