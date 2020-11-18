package ca.ulaval.glo4003.spamdul.entity.charging_point;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingRateTest {

  private final Amount hourlyFee = Amount.valueOf(2.44);
  private final long oneHourInMilliseconds = 60 * 60 * 1000;

  private ChargingRate oneDollarPerHourRate;

  @Before
  public void setUp() {
    oneDollarPerHourRate = new ChargingRate(hourlyFee, TimeUnit.HOURS);
  }

  @Mock
  private RechargULCard card;

  @Test
  public void whenPayingWithZeroTime_shouldNotDebit() {
    oneDollarPerHourRate.pay(0, card);
    verify(card, times(0)).debit(any(Amount.class));
  }

  @Test
  public void whenPayingWithNegativeTime_shouldNotDebit() {
    oneDollarPerHourRate.pay(-123234, card);
    verify(card, times(0)).debit(any(Amount.class));
  }

  @Test
  public void whenPayingWithLessThanOneTimeUnit_shouldDebitExactlyOneUnit() {
    oneDollarPerHourRate.pay(oneHourInMilliseconds - 1, card);
    verify(card, times(1)).debit(hourlyFee);
  }

  @Test
  public void whenPayingWithExactlyOneTimeUnit_shouldDebitExactlyTwoUnits() {
    Amount expectedAmount = hourlyFee.multiply(2);
    oneDollarPerHourRate.pay(oneHourInMilliseconds, card);
    verify(card, times(1)).debit(expectedAmount);
  }

  @Test
  public void whenPayingWithABitMoreThanOneTimeUnit_shouldDebitExactlyTwoUnits() {
    Amount expectedAmount = hourlyFee.multiply(2);
    oneDollarPerHourRate.pay(oneHourInMilliseconds + 1, card);
    verify(card, times(1)).debit(expectedAmount);
  }
}
