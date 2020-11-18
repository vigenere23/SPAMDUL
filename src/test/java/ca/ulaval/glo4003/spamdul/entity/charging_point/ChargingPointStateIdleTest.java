package ca.ulaval.glo4003.spamdul.entity.charging_point;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotChargingException;
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
    when(card.hasUnpaidCharges()).thenReturn(false);
    state.activate(card);
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
