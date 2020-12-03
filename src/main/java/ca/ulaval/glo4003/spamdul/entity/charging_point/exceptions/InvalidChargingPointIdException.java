package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class InvalidChargingPointIdException extends ChargingPointException {

  public InvalidChargingPointIdException() {
    super("this charging point id is invalid");
  }
}
