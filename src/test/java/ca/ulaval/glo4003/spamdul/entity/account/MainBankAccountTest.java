package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainBankAccountTest {

  private MainBankAccount mainBankAccount;
  private final double PERCENT_OF_REVENUE = 0.4;
  private final TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  private final Amount AN_AMOUNT = Amount.valueOf(100);
  private final Amount ANOTHER_AMOUNT = Amount.valueOf(132.32);
  private final LocalDateTime CREATED_AT = LocalDateTime.now();
  private final Transaction A_TRANSACTION = new Transaction(AN_AMOUNT, CREATED_AT, A_TRANSACTION_TYPE);
  private final Transaction ANOTHER_TRANSACTION = new Transaction(ANOTHER_AMOUNT, CREATED_AT, A_TRANSACTION_TYPE);

  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private Account account1;
  @Mock
  private Account account2;

  @Before
  public void setUp() throws Exception {
    mainBankAccount = new MainBankAccount(transactionFactory, account1, account2, PERCENT_OF_REVENUE);
  }

  @Test
  public void whenAddingTransaction_shouldAddTransactionToAccounts() {
    Transaction transaction = new Transaction(AN_AMOUNT, CREATED_AT, A_TRANSACTION_TYPE);
    given(transactionFactory.create(transaction, transaction.getAmount().multiply(PERCENT_OF_REVENUE)))
            .willReturn(A_TRANSACTION);
    given(transactionFactory.create(transaction, transaction.getAmount().multiply(1 - PERCENT_OF_REVENUE)))
            .willReturn(ANOTHER_TRANSACTION);

    mainBankAccount.addTransaction(transaction);

    verify(account1).addTransaction(A_TRANSACTION);
    verify(account2).addTransaction(ANOTHER_TRANSACTION);
  }

  @Test
  public void givenAddedTransaction_whenFindingTransaction_shouldBePresent() {
    Transaction transaction = new Transaction(AN_AMOUNT, CREATED_AT, A_TRANSACTION_TYPE);
    given(transactionFactory.create(transaction, transaction.getAmount().multiply(PERCENT_OF_REVENUE)))
            .willReturn(A_TRANSACTION);
    given(transactionFactory.create(transaction, transaction.getAmount().multiply(1 - PERCENT_OF_REVENUE)))
            .willReturn(ANOTHER_TRANSACTION);
  }
}
