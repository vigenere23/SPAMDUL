package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public abstract class ChargingPointException extends RuntimeException {

  public ChargingPointException(String message) {
    super(message);
  }
}
