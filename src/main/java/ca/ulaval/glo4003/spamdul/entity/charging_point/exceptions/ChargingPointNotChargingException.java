package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointNotChargingException extends ChargingPointException {

  public ChargingPointNotChargingException() {
    super("the charging point must be charging first");
  }
}
