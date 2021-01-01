package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointAlreadyChargingException;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointNotDisconnectedException;
import ca.ulaval.glo4003.spamdul.shared.entities.counter.MillisecondsCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointStateChargingTest {

  private ChargingPointStateCharging state;

  @Mock
  private ChargingPoint chargingPoint;
  @Mock
  private MillisecondsCounter millisecondsCounter;

  @Before
  public void setUp() {
    state = new ChargingPointStateCharging(chargingPoint, millisecondsCounter);
  }

  @Test
  public void whenDisconnecting_shouldStopCounter() {
    state.disconnect();

    verify(millisecondsCounter).stop();
  }

  @Test
  public void whenDisconnecting_shouldSwitchToIdleState() {
    state.disconnect();

    verify(chargingPoint).setState(any(ChargingPointStateIdle.class));
  }

  @Test(expected = ChargingPointNotDisconnectedException.class)
  public void whenActivating_shouldThrowError() {
    state.activate();
  }

  @Test(expected = ChargingPointAlreadyChargingException.class)
  public void whenConnecting_shouldThrowError() {
    state.connect();
  }
}
