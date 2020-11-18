package ca.ulaval.glo4003.spamdul.entity.charging_counter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions.ChargingCounterNotStartedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingCounterStateIdleTest {

  private ChargingCounterStateIdle state;

  @Mock
  private ChargingCounter chargingCounter;

  @Before
  public void setUp() {
    state = new ChargingCounterStateIdle(chargingCounter);
  }

  @Test
  public void whenStarting_shouldSwitchToCountingState() {
    state.start();
    verify(chargingCounter).setState(any(ChargingCounterStateCounting.class));
  }

  @Test(expected = ChargingCounterNotStartedException.class)
  public void whenStopping_shouldThrowError() {
    state.stop();
  }
}
