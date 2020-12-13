package ca.ulaval.glo4003.spamdul.shared.counter;

import static com.google.common.truth.Truth.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MillisecondsCounterTest {

  private final long MILLISECONDS_DURATION = 1273458;
  private final Instant END_TIME = Instant.now();
  private final Instant START_TIME = END_TIME.minusMillis(MILLISECONDS_DURATION);
  private final long MILLISECONDS_TO_ADD = 21433456;

  @Test
  public void calculatesCorrectDuration() {
    Clock startClock = Clock.fixed(START_TIME, ZoneId.systemDefault());
    MillisecondsCounter counter = new MillisecondsCounter(startClock);
    counter.start(0);

    Clock endClock = Clock.fixed(END_TIME, ZoneId.systemDefault());
    counter.setClock(endClock);

    long milliseconds = counter.stop();
    assertThat(milliseconds).isEqualTo(MILLISECONDS_DURATION);
  }

  @Test
  public void addsMillisecondsToAddToDuration() {
    Clock startClock = Clock.fixed(START_TIME, ZoneId.systemDefault());
    MillisecondsCounter counter = new MillisecondsCounter(startClock);
    counter.start(MILLISECONDS_TO_ADD);

    Clock endClock = Clock.fixed(END_TIME, ZoneId.systemDefault());
    counter.setClock(endClock);

    long milliseconds = counter.stop();
    assertThat(milliseconds).isEqualTo(MILLISECONDS_DURATION + MILLISECONDS_TO_ADD);
  }
}
