package ca.ulaval.glo4003.spamdul.entity.charging_counter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions.ChargingCounterAlreadyStartedException;
import ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions.ChargingCounterNotStartedException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingCounterTest {

  @Mock
  private RechargULCard card;

  @Test(expected = NotEnoughCreditsException.class)
  public void whenInitializingWithNotEnoughCredits_shouldThrowError() {
    when(card.hasUnpaidCharges()).thenReturn(true);
    new ChargingCounter(card);
  }

  @Test
  public void givenInitialized_whenStarting_shouldStart() {
    when(card.hasUnpaidCharges()).thenReturn(false);
    ChargingCounter counter = new ChargingCounter(card);
    counter.start();
  }

  @Test(expected = ChargingCounterNotStartedException.class)
  public void givenInitialized_whenStopping_shouldThrowError() {
    when(card.hasUnpaidCharges()).thenReturn(false);
    ChargingCounter counter = new ChargingCounter(card);
    counter.stop();
  }

  @Test
  public void givenStarted_whenStopping_shouldDebit() {
    when(card.hasUnpaidCharges()).thenReturn(false);
    ChargingCounter counter = new ChargingCounter(card);
    counter.start();

    counter.stop();
    verify(card, times(1)).debit(any(Amount.class));
  }

  @Test(expected = ChargingCounterAlreadyStartedException.class)
  public void givenStarted_whenStarting_shouldThrowError() {
    when(card.hasUnpaidCharges()).thenReturn(false);
    ChargingCounter counter = new ChargingCounter(card);
    counter.start();

    counter.start();
  }
}
