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
public class InfractionBankAccountTest {

  private final Amount AN_AMOUNT = Amount.valueOf(834.23);

  @Mock
  private MainBankAccount mainBankAccount;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;
  @Mock
  private TransactionAmountQueryer A_TRANSACTION_AMOUNT_QUERYER;

  private InfractionBankAccount infractionBankAccount;

  @Before
  public void setUp() {
    infractionBankAccount = new InfractionBankAccount(mainBankAccount, sustainabilityBankAccount);
  }

  @Test
  public void whenAddingRevenue_shouldAdd40PercentToSustainabilityRevenue() {
    infractionBankAccount.addRevenue(AN_AMOUNT);
    verify(sustainabilityBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.4), TransactionType.INFRACTION);
  }

  @Test
  public void whenAddingRevenue_shouldAdd60PercentToMainRevenue() {
    infractionBankAccount.addRevenue(AN_AMOUNT);
    verify(mainBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.6), TransactionType.INFRACTION);
  }

  @Test
  public void whenGettingRevenueForSustainability_shouldReturnFromSustainabilityAccount() {
    when(sustainabilityBankAccount.getRevenue()).thenReturn(A_TRANSACTION_AMOUNT_QUERYER);
    when(A_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.INFRACTION, A_TRANSACTION_FILTER)).thenReturn(AN_AMOUNT);
    Amount revenue = infractionBankAccount.getRevenueForSustainability(A_TRANSACTION_FILTER);
    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }
}
