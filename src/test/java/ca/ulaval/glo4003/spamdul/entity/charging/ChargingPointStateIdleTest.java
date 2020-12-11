package ca.ulaval.glo4003.spamdul.entity.charging;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointStateIdleTest {

  private ChargingPointStateIdle state;

  @Mock
  private ChargingPoint chargingPoint;
  @Mock
  private RechargULCard card;

  @Before
  public void setUp() {
    state = new ChargingPointStateIdle(chargingPoint);
  }

  @Test
  public void whenActivatingWithEnoughCreditsCard_shouldSwitchToActivatedState() {
    state.activate();
    verify(chargingPoint).setState(any(ChargingPointStateActivated.class));
  }

  @Test(expected = ChargingPointNotActivatedException.class)
  public void whenConnecting_shouldThrowError() {
    state.connect();
  }

  @Test(expected = ChargingPointNotChargingException.class)
  public void whenDisconnecting_shouldThrowError() {
    state.disconnect();
  }
}
