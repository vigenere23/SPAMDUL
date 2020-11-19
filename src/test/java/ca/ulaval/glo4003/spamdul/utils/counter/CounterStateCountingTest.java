package ca.ulaval.glo4003.spamdul.utils.counter;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.utils.counter.exceptions.CounterAlreadyStartedException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CounterStateCountingTest {

  private final long MILLISECONDS_DURATION = 21483;
  private final Instant END_TIME = Instant.now();
  private final Instant START_TIME = END_TIME.minusMillis(MILLISECONDS_DURATION);
  private final long MILLISECONDS_TO_ADD = 89678972;

  @Mock
  private MillisecondsCounter counter;
  @Mock
  private Clock clock;

  @Test
  public void whenStopping_shouldReturnNumberOfMilliseconds() {
    Clock endClock = Clock.fixed(END_TIME, ZoneId.systemDefault());
    CounterStateCounting state = new CounterStateCounting(counter, START_TIME, 0);

    long milliseconds = state.stop(endClock);
    assertThat(milliseconds).isEqualTo(MILLISECONDS_DURATION);
  }

  @Test
  public void givenMillisecondsToAdd_whenStopping_shouldReturnNumberOfMillisecondsPlusMillisecondsToAdd() {
    Clock endClock = Clock.fixed(END_TIME, ZoneId.systemDefault());
    CounterStateCounting state = new CounterStateCounting(counter, START_TIME, MILLISECONDS_TO_ADD);

    long milliseconds = state.stop(endClock);
    assertThat(milliseconds).isEqualTo(MILLISECONDS_DURATION + MILLISECONDS_TO_ADD);
  }

  @Test
  public void whenStopping_shouldSwitchToIdleState() {
    CounterStateCounting state = new CounterStateCounting(counter, START_TIME, 0);
    when(clock.instant()).thenReturn(END_TIME);
    state.stop(clock);
    verify(counter).setState(any(CounterStateIdle.class));
  }

  @Test(expected = CounterAlreadyStartedException.class)
  public void whenStarting_shouldThrowException() {
    CounterStateCounting state = new CounterStateCounting(counter, START_TIME, 0);
    state.start(clock, 0);
  }
}
