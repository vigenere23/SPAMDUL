package ca.ulaval.glo4003.spamdul.shared.counter.exceptions;

public class CounterNotStartedException extends CounterException {

  public CounterNotStartedException() {
    super("The counter must be started first");
  }
}
