package ca.ulaval.glo4003.spamdul.shared.counter;

import ca.ulaval.glo4003.spamdul.shared.counter.exceptions.CounterNotStartedException;
import java.time.Clock;

public class CounterStateIdle implements CounterState {

  private final MillisecondsCounter millisecondsCounter;

  public CounterStateIdle(MillisecondsCounter millisecondsCounter) {
    this.millisecondsCounter = millisecondsCounter;
  }

  @Override public void start(Clock clock) {
    start(clock, 0);
  }

  @Override public void start(Clock clock, long millisecondsToAdd) {
    millisecondsCounter.setState(new CounterStateCounting(millisecondsCounter, clock.instant(), millisecondsToAdd));
  }

  @Override public long stop(Clock clock) {
    throw new CounterNotStartedException();
  }
}
