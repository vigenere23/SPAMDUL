package ca.ulaval.glo4003.spamdul.shared.entities.counter.exceptions;

public class CounterAlreadyStartedException extends CounterException {

  public CounterAlreadyStartedException() {
    super("The counter has already started");
  }
}
