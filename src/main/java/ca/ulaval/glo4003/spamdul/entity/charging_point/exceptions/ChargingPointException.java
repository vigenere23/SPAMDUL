package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public abstract class ChargingPointException extends RuntimeException {

  public ChargingPointException(String message) {
    super(message);
  }
}
