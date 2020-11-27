package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionAmountQueryer;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassBankAccountTest {

  private final Amount AN_AMOUNT = Amount.valueOf(234.23);
  private final Amount ANOTHER_AMOUNT = Amount.valueOf(42.21);

  @Mock
  private MainBankAccount mainBankAccount;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;
  @Mock
  private TransactionAmountQueryer A_TRANSACTION_AMOUNT_QUERYER;
  @Mock
  private TransactionAmountQueryer ANOTHER_TRANSACTION_AMOUNT_QUERYER;

  private PassBankAccount passBankAccount;

  @Before
  public void setUp() {
    passBankAccount = new PassBankAccount(mainBankAccount, sustainabilityBankAccount);
  }

  @Test
  public void whenAddingRevenue_shouldAdd40PercentToSustainabilityRevenue() {
    passBankAccount.addRevenue(AN_AMOUNT);
    verify(sustainabilityBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.4), TransactionType.PASS);
  }

  @Test
  public void whenAddingRevenue_shouldAdd60PercentToMainRevenue() {
    passBankAccount.addRevenue(AN_AMOUNT);
    verify(mainBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.6), TransactionType.PASS);
  }

  @Test
  public void whenGettingRevenueForSustainability_shouldReturnFromSustainableAccount() {
    when(sustainabilityBankAccount.getRevenue()).thenReturn(A_TRANSACTION_AMOUNT_QUERYER);
    when(A_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.PASS, A_TRANSACTION_FILTER)).thenReturn(AN_AMOUNT);
    Amount revenue = passBankAccount.getRevenueForSustainability(A_TRANSACTION_FILTER);
    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenue_shouldReturnFromSustainableAndMainAccounts() {
    when(sustainabilityBankAccount.getRevenue()).thenReturn(A_TRANSACTION_AMOUNT_QUERYER);
    when(mainBankAccount.getRevenue()).thenReturn(ANOTHER_TRANSACTION_AMOUNT_QUERYER);
    when(ANOTHER_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.PASS, A_TRANSACTION_FILTER)).thenReturn(AN_AMOUNT);
    when(A_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.PASS, A_TRANSACTION_FILTER)).thenReturn(ANOTHER_AMOUNT);

    Amount revenue = passBankAccount.getRevenue(A_TRANSACTION_FILTER);

    assertThat(revenue).isEqualTo(AN_AMOUNT.add(ANOTHER_AMOUNT));
  }
}
