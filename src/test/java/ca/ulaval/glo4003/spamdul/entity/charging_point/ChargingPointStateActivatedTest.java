package ca.ulaval.glo4003.spamdul.entity.charging_point;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.ChargingCounter;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyActivatedException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
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
  private ChargingCounter chargingCounter;
  @Mock
  private RechargULCard card;

  @Before
  public void setUp() {
    state = new ChargingPointStateActivated(chargingPoint, chargingCounter);
  }

  @Test
  public void whenConnecting_shouldStartCounterAndSwitchToChargingState() {
    state.connect();
    verify(chargingCounter, times(1)).start();
    verify(chargingPoint).setState(any(ChargingPointStateCharging.class));
  }

  @Test(expected = ChargingPointAlreadyActivatedException.class)
  public void whenActivating_shouldThrowError() {
    state.activate(card);
  }

  @Test
  public void whenDisconnecting_shouldSwitchToIdleState() {
    state.disconnect();
    verify(chargingPoint).setState(any(ChargingPointStateIdle.class));
  }
}
