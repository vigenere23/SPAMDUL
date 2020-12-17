package ca.ulaval.glo4003.spamdul.shared.entities.counter.exceptions;

public abstract class CounterException extends RuntimeException {

  protected CounterException(String message) {
    super(message);
  }
}
