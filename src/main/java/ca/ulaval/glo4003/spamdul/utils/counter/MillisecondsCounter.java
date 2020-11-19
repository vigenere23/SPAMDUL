package ca.ulaval.glo4003.spamdul.utils.counter;

import java.time.Clock;

public class MillisecondsCounter {

  private CounterState state = new CounterStateIdle(this);
  private Clock clock;

  public MillisecondsCounter(Clock clock) {
    setClock(clock);
  }

  public void setClock(Clock clock) {
    this.clock = clock;
  }

  public void start(long millisecondsToAdd) {
    state.start(clock, millisecondsToAdd);
  }

  public long stop() {
    return state.stop(clock);
  }

  public void setState(CounterState state) {
    this.state = state;
  }
}
