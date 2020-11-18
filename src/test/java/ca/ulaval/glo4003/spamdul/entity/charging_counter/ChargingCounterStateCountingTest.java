package ca.ulaval.glo4003.spamdul.entity.charging_counter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions.ChargingCounterAlreadyStartedException;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingCounterStateCountingTest {

  private final LocalDateTime A_DATETIME = LocalDateTime.now();
  private final LocalDateTime zeroHourStartTime = LocalDateTime.now();
  private final LocalDateTime tenHoursStartTime = zeroHourStartTime.minusHours(10);

  @Mock
  private ChargingCounter chargingCounter;

  @Test
  public void whenStoppingWithZeroHourDifference_shouldPayForOneHour() {
    ChargingCounterStateCounting state = new ChargingCounterStateCounting(chargingCounter, zeroHourStartTime);
    long expectedNumberOfHoursPaid = 1;

    state.stop();

    verify(chargingCounter).pay(expectedNumberOfHoursPaid);
  }

  @Test
  public void whenStoppingWithTenHoursDifference_shouldPayForElevenHours() {
    ChargingCounterStateCounting state = new ChargingCounterStateCounting(chargingCounter, tenHoursStartTime);
    long expectedNumberOfHoursPaid = 11;

    state.stop();

    verify(chargingCounter).pay(expectedNumberOfHoursPaid);
  }

  @Test
  public void whenStopping_shouldSwitchToIdleState() {
    new ChargingCounterStateCounting(chargingCounter, A_DATETIME).stop();
    verify(chargingCounter).setState(any(ChargingCounterStateIdle.class));
  }

  @Test(expected = ChargingCounterAlreadyStartedException.class)
  public void whenStarting_shouldThrowError() {
    new ChargingCounterStateCounting(chargingCounter, A_DATETIME).start();
  }
}
