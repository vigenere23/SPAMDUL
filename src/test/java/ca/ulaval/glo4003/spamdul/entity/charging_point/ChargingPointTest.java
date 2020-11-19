package ca.ulaval.glo4003.spamdul.entity.charging_point;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.utils.counter.MillisecondsCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointTest {

  private ChargingPoint chargingPoint;

  @Mock
  private RechargULCard rechargULCard;
  @Mock
  private ChargingPointState state;
  @Mock
  private MillisecondsCounter counter;
  @Mock
  private ChargingPaymentService chargingPaymentService;

  @Before
  public void setUp() {
    chargingPoint = new ChargingPoint(new ChargingPointId(), chargingPaymentService);
  }

  @Test
  public void givenInitialized_whenActivatingWithEnoughCreditsCard_shouldActivate() {
    chargingPoint.activate(rechargULCard);
  }

  @Test(expected = NotEnoughCreditsException.class)
  public void givenInitialized_whenActivatingWithNotEnoughCreditsCard_shouldThrowException() {
    doThrow(NotEnoughCreditsException.class).when(rechargULCard).verifyHasEnoughCredits();
    chargingPoint.activate(rechargULCard);
  }

  @Test(expected = ChargingPointNotActivatedException.class)
  public void givenInitialized_whenConnecting_shouldThrowException() {
    chargingPoint.connect();
  }

  @Test(expected = ChargingPointNotChargingException.class)
  public void givenInitialized_whenDisconnecting_shouldThrowException() {
    chargingPoint.disconnect();
  }

  @Test(expected = ChargingPointNotActivatedException.class)
  public void givenInitialized_whenDeactivating_shouldThrowException() {
    chargingPoint.deactivate();
  }

  @Test
  public void givenEnoughCreditsCard_whenActivating_shouldDelegateToState() {
    chargingPoint.setState(state);

    chargingPoint.activate(rechargULCard);

    verify(state).activate();
  }

  @Test
  public void whenConnecting_shouldDelegateToState() {
    chargingPoint.setState(state);
    chargingPoint.connect();
    verify(state).connect();
  }

  @Test
  public void whenDisconnecting_shouldDelegateToState() {
    chargingPoint.setState(state);
    chargingPoint.disconnect();
    verify(state).disconnect();
  }

  @Test
  public void whenDeactivating_shouldDelegateToState() {
    chargingPoint.activate(rechargULCard);
    chargingPoint.setState(state);

    chargingPoint.deactivate();

    verify(state).deactivate();
  }

  @Test
  public void givenCharged_whenDeactivating_shouldDelegatePaymentToChargingRate() {
    long millisecondsUsed = 123471239;
    when(state.deactivate()).thenReturn(millisecondsUsed);
    chargingPoint.activate(rechargULCard);
    chargingPoint.setState(state);

    chargingPoint.deactivate();

    verify(chargingPaymentService).pay(millisecondsUsed, rechargULCard);
  }
}
