package ca.ulaval.glo4003.spamdul.entity.charging_point;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyChargingException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotDisconnectedException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.utils.Amount;
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

  @Before
  public void setUp() {
    chargingPoint = new ChargingPoint(new ChargingPointId());
  }

  @Test
  public void givenInitialized_whenActivatingWithEnoughCreditsCard_shouldActivate() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
  }

  @Test(expected = NotEnoughCreditsException.class)
  public void givenInitialized_whenActivatingWithNotEnoughCreditsCard_shouldThrowException() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(true);
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

  @Test
  public void givenActivated_whenConnecting_shouldStartCount() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
    chargingPoint.connect();
  }

  @Test
  public void givenActivated_whenDisconnecting_shouldNotDebit() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
    
    chargingPoint.disconnect();

    verify(rechargULCard, times(0)).debit(any(Amount.class));
  }

  @Test(expected = ChargingPointNotChargingException.class)
  public void givenDisconnected_whenDisconnecting_shouldThrowError() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
    chargingPoint.disconnect();

    chargingPoint.disconnect();
  }

  @Test(expected = ChargingPointAlreadyActivatedException.class)
  public void givenActivated_whenActivating_shouldThrowException() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);

    chargingPoint.activate(rechargULCard);
  }

  @Test
  public void givenActivated_whenConnecting_shouldNotDebit() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);

    chargingPoint.connect();

    verify(rechargULCard, times(0)).debit(any(Amount.class));
  }

  @Test(expected = ChargingPointAlreadyChargingException.class)
  public void givenConnected_whenConnecting_shouldThrowException() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
    chargingPoint.connect();

    chargingPoint.connect();
  }

  @Test(expected = ChargingPointNotDisconnectedException.class)
  public void givenConnected_whenActivating_shouldThrowException() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
    chargingPoint.connect();

    chargingPoint.activate(rechargULCard);
  }

  @Test
  public void givenConnected_whenDisconnected_shouldDebit() {
    when(rechargULCard.hasUnpaidCharges()).thenReturn(false);
    chargingPoint.activate(rechargULCard);
    chargingPoint.connect();

    chargingPoint.disconnect();

    verify(rechargULCard, times(1)).debit(any(Amount.class));
  }
}
