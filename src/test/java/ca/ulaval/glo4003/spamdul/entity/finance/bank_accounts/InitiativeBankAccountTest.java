package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeBankAccountTest {

  private final Amount AN_AMOUNT = Amount.valueOf(235.12);

  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;

  private InitiativeBankAccount initiativeBankAccount;

  @Before
  public void setUp() {
    initiativeBankAccount = new InitiativeBankAccount(sustainabilityBankAccount);
  }

  @Test
  public void whenAddingExpense_shouldAddToSustainabilityExpenses() {
    initiativeBankAccount.addExpense(AN_AMOUNT);
    verify(sustainabilityBankAccount, times(1)).addExpense(AN_AMOUNT, TransactionType.INITIATIVE);
  }
}
