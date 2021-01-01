package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointAlreadyActivatedException;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.shared.entities.counter.MillisecondsCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointStateActivatedTest {

  private ChargingPointStateActivated state;

  @Mock
  private ChargingPoint chargingPoint;
  @Mock
  private MillisecondsCounter millisecondsCounter;

  @Before
  public void setUp() {
    state = new ChargingPointStateActivated(chargingPoint, millisecondsCounter, 0);
  }

  @Test
  public void whenConnecting_shouldStartCounterWithMillisecondsToAdd() {
    long millisecondsToAdd = 89787423;
    state = new ChargingPointStateActivated(chargingPoint, millisecondsCounter, millisecondsToAdd);

    state.connect();

    verify(millisecondsCounter).start(millisecondsToAdd);
  }

  @Test
  public void whenConnecting_shouldSwitchToChargingState() {
    state.connect();

    verify(chargingPoint).setState(any(ChargingPointStateCharging.class));
  }

  @Test(expected = ChargingPointAlreadyActivatedException.class)
  public void whenActivating_shouldThrowError() {
    state.activate();
  }

  @Test(expected = ChargingPointNotChargingException.class)
  public void whenDisconnecting_shouldThrowError() {
    state.disconnect();
  }

  @Test
  public void whenDeactivating_shouldSwitchToIdleState() {
    state.deactivate();

    verify(chargingPoint).setState(any(ChargingPointStateIdle.class));
  }

  @Test
  public void whenDeactivating_shouldReturnMillisecondsUsed() {
    long expectedMillisecondsUsed = 9378234;
    state = new ChargingPointStateActivated(chargingPoint, millisecondsCounter, expectedMillisecondsUsed);

    long millisecondsUsed = state.deactivate();

    assertThat(millisecondsUsed).isEqualTo(expectedMillisecondsUsed);
  }
}
