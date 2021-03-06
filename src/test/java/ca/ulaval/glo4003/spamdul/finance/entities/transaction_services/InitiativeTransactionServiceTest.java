package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeTransactionServiceTest {

  private final Amount AN_AMOUNT = Amount.valueOf(235.12);

  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;

  private InitiativeTransactionService initiativeTransactionService;

  @Before
  public void setUp() {
    initiativeTransactionService = new InitiativeTransactionService(sustainabilityBankAccount);
  }

  @Test
  public void whenAddingExpense_shouldAddToSustainabilityExpenses() {
    initiativeTransactionService.addExpense(AN_AMOUNT);

    verify(sustainabilityBankAccount).addExpense(AN_AMOUNT, TransactionType.INITIATIVE);
  }
}
