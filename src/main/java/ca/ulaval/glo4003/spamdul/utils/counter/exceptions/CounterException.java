package ca.ulaval.glo4003.spamdul.utils.counter.exceptions;

public abstract class CounterException extends RuntimeException {

  protected CounterException(String message) {
    super(message);
  }
}
