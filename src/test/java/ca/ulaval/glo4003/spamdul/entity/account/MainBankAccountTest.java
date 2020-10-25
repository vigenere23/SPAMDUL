package ca.ulaval.glo4003.spamdul.entity.account;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class MainBankAccountTest {

  private MainBankAccount mainBankAccount;
  private double PERCENT_OF_REVENUE = 0.4;
  private TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  private Amount AN_AMOUNT = Amount.valueOf(100);
  private LocalDateTime CREATED_AT = LocalDateTime.now();


  @Mock
  private Account account1 = mock(Account.class);
  @Mock
  private Account account2 = mock(Account.class);


  @Before
  public void setUp() throws Exception {
    mainBankAccount = new MainBankAccount(account1, account2, PERCENT_OF_REVENUE);
  }

  @Test
  public void whenAddingTransacton_shouldAddTransactionToAccounts() {
    Transaction transaction = new Transaction(AN_AMOUNT, CREATED_AT, A_TRANSACTION_TYPE);
    mainBankAccount.addTransaction(transaction);


  }
}