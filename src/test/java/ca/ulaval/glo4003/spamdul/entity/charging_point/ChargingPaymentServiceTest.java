package ca.ulaval.glo4003.spamdul.entity.charging_point;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPaymentServiceTest {
  private static RechargULCardId A_CARD_ID = new RechargULCardId();

  private final Amount hourlyFee = Amount.valueOf(2.44);
  private final long oneHourInMilliseconds = 60 * 60 * 1000;

  @Mock
  private User user;
  @Mock
  private UserRepository userRepository;

  private ChargingPaymentService oneDollarPerHourRate;

  @Before
  public void setUp() {
    oneDollarPerHourRate = new ChargingPaymentService(hourlyFee, TimeUnit.HOURS, userRepository);
    when(userRepository.findBy(A_CARD_ID)).thenReturn(user);
  }

  @Test
  public void whenPayingWithZeroTime_shouldNotDebit() {
    oneDollarPerHourRate.pay(0, A_CARD_ID);
    verify(user, times(0)).payForCharging(any(Amount.class));
  }

  @Test
  public void whenPayingWithNegativeTime_shouldNotDebit() {
    oneDollarPerHourRate.pay(-123234, A_CARD_ID);
    verify(user, times(0)).payForCharging(any(Amount.class));
  }

  @Test
  public void whenPaying_shouldFindUserInRepos() {
    oneDollarPerHourRate.pay(oneHourInMilliseconds - 1, A_CARD_ID);
    verify(userRepository).findBy(A_CARD_ID);
  }

  @Test
  public void whenPayingWithLessThanOneTimeUnit_shouldDebitExactlyOneUnit() {
    oneDollarPerHourRate.pay(oneHourInMilliseconds - 1, A_CARD_ID);
    verify(user).payForCharging(hourlyFee);
  }

  @Test
  public void whenPayingWithExactlyOneTimeUnit_shouldDebitExactlyTwoUnits() {
    Amount expectedAmount = hourlyFee.multiply(2);
    oneDollarPerHourRate.pay(oneHourInMilliseconds, A_CARD_ID);
    verify(user).payForCharging(expectedAmount);
  }

  @Test
  public void whenPayingWithABitMoreThanOneTimeUnit_shouldDebitExactlyTwoUnits() {
    Amount expectedAmount = hourlyFee.multiply(2);
    oneDollarPerHourRate.pay(oneHourInMilliseconds + 1, A_CARD_ID);
    verify(user).payForCharging(expectedAmount);
  }

  @Test
  public void whenPaying_shouldSaveUserInRepo() {
    oneDollarPerHourRate.pay(oneHourInMilliseconds - 1, A_CARD_ID);
    verify(userRepository).save(user);
  }

}
