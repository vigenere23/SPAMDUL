package ca.ulaval.glo4003.spamdul.entity.finance.transaction_services;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionAmountQuerier;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassTransactionServiceTest {

  private final Amount AN_AMOUNT = Amount.valueOf(234.23);
  private final Amount ANOTHER_AMOUNT = Amount.valueOf(42.21);

  @Mock
  private MainBankAccount mainBankAccount;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;
  @Mock
  private TransactionAmountQuerier A_TRANSACTION_AMOUNT_QUERYER;
  @Mock
  private TransactionAmountQuerier ANOTHER_TRANSACTION_AMOUNT_QUERYER;

  private PassTransactionService passTransactionService;

  @Before
  public void setUp() {
    passTransactionService = new PassTransactionService(mainBankAccount, sustainabilityBankAccount);
  }

  @Test
  public void whenAddingRevenue_shouldAdd40PercentToSustainabilityRevenue() {
    passTransactionService.addRevenue(AN_AMOUNT);
    verify(sustainabilityBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.4), TransactionType.PASS);
  }

  @Test
  public void whenAddingRevenue_shouldAdd60PercentToMainRevenue() {
    passTransactionService.addRevenue(AN_AMOUNT);
    verify(mainBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.6), TransactionType.PASS);
  }

  @Test
  public void whenGettingRevenueForSustainability_shouldReturnFromSustainableAccount() {
    when(sustainabilityBankAccount.getRevenue()).thenReturn(A_TRANSACTION_AMOUNT_QUERYER);
    when(A_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.PASS, A_TRANSACTION_FILTER)).thenReturn(AN_AMOUNT);
    Amount revenue = passTransactionService.getRevenueForSustainability(A_TRANSACTION_FILTER);
    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenue_shouldReturnFromSustainableAndMainAccounts() {
    when(sustainabilityBankAccount.getRevenue()).thenReturn(A_TRANSACTION_AMOUNT_QUERYER);
    when(mainBankAccount.getRevenue()).thenReturn(ANOTHER_TRANSACTION_AMOUNT_QUERYER);
    when(ANOTHER_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.PASS, A_TRANSACTION_FILTER)).thenReturn(AN_AMOUNT);
    when(A_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.PASS, A_TRANSACTION_FILTER)).thenReturn(ANOTHER_AMOUNT);

    Amount revenue = passTransactionService.getRevenue(A_TRANSACTION_FILTER);

    assertThat(revenue).isEqualTo(AN_AMOUNT.add(ANOTHER_AMOUNT));
  }
}
