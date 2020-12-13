package ca.ulaval.glo4003.spamdul.shared.counter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.shared.counter.exceptions.CounterNotStartedException;
import java.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CounterStateIdleTest {

  private CounterStateIdle state;

  @Mock
  private MillisecondsCounter counter;
  @Mock
  private Clock clock;

  @Before
  public void setUp() {
    state = new CounterStateIdle(counter);
  }

  @Test
  public void whenStarting_shouldSwitchToCountingState() {
    state.start(clock, 0);
    verify(counter).setState(any(CounterStateCounting.class));
  }

  @Test(expected = CounterNotStartedException.class)
  public void whenStopping_shouldThrowError() {
    state.stop(clock);
  }
}
