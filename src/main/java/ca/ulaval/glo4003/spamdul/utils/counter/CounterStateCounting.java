package ca.ulaval.glo4003.spamdul.utils.counter;

import ca.ulaval.glo4003.spamdul.utils.counter.exceptions.CounterAlreadyStartedException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CounterStateCounting implements CounterState {

  private final MillisecondsCounter millisecondsCounter;
  private final long millisecondsToAdd;
  private final Instant startTime;

  public CounterStateCounting(MillisecondsCounter millisecondsCounter,
                              Instant startTime,
                              long millisecondsToAdd) {
    this.startTime = startTime;
    this.millisecondsCounter = millisecondsCounter;
    this.millisecondsToAdd = millisecondsToAdd;
  }

  @Override public void start(Clock clock) {
    throw new CounterAlreadyStartedException();
  }

  @Override public void start(Clock clock, long millisecondsToAdd) {
    throw new CounterAlreadyStartedException();
  }

  @Override public long stop(Clock clock) {
    Instant endTime = clock.instant();
    long milliseconds = ChronoUnit.MILLIS.between(startTime, endTime);
    millisecondsCounter.setState(new CounterStateIdle(millisecondsCounter));

    return milliseconds + millisecondsToAdd;
  }
}
