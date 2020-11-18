package ca.ulaval.glo4003.spamdul.utils.counter;

import java.time.Clock;

public interface CounterState {

  void start(Clock clock);

  void start(Clock clock, long millisecondsToAdd);

  long stop(Clock clock);
}
