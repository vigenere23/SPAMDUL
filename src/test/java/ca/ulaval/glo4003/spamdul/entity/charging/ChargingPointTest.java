package ca.ulaval.glo4003.spamdul.entity.charging;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointTest {

  private static final RechargULCardId A_CARD_ID = RechargULCardId.valueOf("123");
  private static final ChargingPointId A_CHARGING_POINT_ID = ChargingPointId.valueOf("123");

  private ChargingPoint chargingPoint;

  @Mock
  private ChargingPointState state;
  @Mock
  private ChargingPaymentService chargingPaymentService;
  @Mock
  private EnoughCreditForChargingVerifier creditVerifier;

  @Before
  public void setUp() {
    chargingPoint = new ChargingPoint(A_CHARGING_POINT_ID);
  }

  @Test
  public void givenInitialized_whenActivatingWithEnoughCreditsCard_shouldActivate() {
    chargingPoint.activate(creditVerifier, A_CARD_ID);
  }

  @Test(expected = NotEnoughCreditsException.class)
  public void givenInitialized_whenActivatingWithNotEnoughCreditsCard_shouldThrowException() {
    doThrow(NotEnoughCreditsException.class).when(creditVerifier).verify(A_CARD_ID);

    chargingPoint.activate(creditVerifier, A_CARD_ID);
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
    chargingPoint.deactivateAndPay(chargingPaymentService);
  }

  @Test
  public void givenEnoughCreditsCard_whenActivating_shouldDelegateToState() {
    chargingPoint.setState(state);

    chargingPoint.activate(creditVerifier, A_CARD_ID);

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
    chargingPoint.activate(creditVerifier, A_CARD_ID);
    chargingPoint.setState(state);

    chargingPoint.deactivateAndPay(chargingPaymentService);

    verify(state).deactivate();
  }

  @Test
  public void givenCharged_whenDeactivating_shouldDelegatePaymentToChargingRate() {
    long millisecondsUsed = 123471239;
    when(state.deactivate()).thenReturn(millisecondsUsed);
    chargingPoint.activate(creditVerifier, A_CARD_ID);
    chargingPoint.setState(state);

    chargingPoint.deactivateAndPay(chargingPaymentService);

    verify(chargingPaymentService).pay(millisecondsUsed, A_CARD_ID);
  }
}
