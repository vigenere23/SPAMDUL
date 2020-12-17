package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionAmountQuerier;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionTransactionServiceTest {

  private final Amount AN_AMOUNT = Amount.valueOf(834.23);

  @Mock
  private MainBankAccount mainBankAccount;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;
  @Mock
  private TransactionAmountQuerier A_TRANSACTION_AMOUNT_QUERYER;

  private InfractionTransactionService infractionTransactionService;

  @Before
  public void setUp() {
    infractionTransactionService = new InfractionTransactionService(mainBankAccount, sustainabilityBankAccount);
  }

  @Test
  public void whenAddingRevenue_shouldAdd40PercentToSustainabilityRevenue() {
    infractionTransactionService.addRevenue(AN_AMOUNT);

    verify(sustainabilityBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.4), TransactionType.INFRACTION);
  }

  @Test
  public void whenAddingRevenue_shouldAdd60PercentToMainRevenue() {
    infractionTransactionService.addRevenue(AN_AMOUNT);

    verify(mainBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.6), TransactionType.INFRACTION);
  }

  @Test
  public void whenGettingRevenueForSustainability_shouldReturnFromSustainabilityAccount() {
    when(sustainabilityBankAccount.getRevenue()).thenReturn(A_TRANSACTION_AMOUNT_QUERYER);
    when(A_TRANSACTION_AMOUNT_QUERYER.with(TransactionType.INFRACTION, A_TRANSACTION_FILTER)).thenReturn(AN_AMOUNT);

    Amount revenue = infractionTransactionService.getRevenueForSustainability(A_TRANSACTION_FILTER);

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }
}
