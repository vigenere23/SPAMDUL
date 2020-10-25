package ca.ulaval.glo4003.spamdul.entity.account;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


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
  private Account sustainableAccount;
  @Mock
  private Account otherAccount;

  @Before
  public void setUp() throws Exception {
    mainBankAccount = new MainBankAccount(transactionFactory, sustainableAccount, otherAccount, PERCENT_OF_REVENUE);
  }

  @Test
  public void whenAddingTransaction_shouldAddTransactionToAccounts() {
    Transaction transaction = new Transaction(AN_AMOUNT, CREATED_AT, A_TRANSACTION_TYPE);
    when(transactionFactory.create(transaction, transaction.getAmount().multiply(PERCENT_OF_REVENUE)))
        .thenReturn(A_TRANSACTION);
    when(transactionFactory.create(transaction, transaction.getAmount().multiply(1 - PERCENT_OF_REVENUE)))
        .thenReturn(ANOTHER_TRANSACTION);

    mainBankAccount.addTransaction(transaction);

    verify(sustainableAccount).addTransaction(A_TRANSACTION);
    verify(otherAccount).addTransaction(ANOTHER_TRANSACTION);
  }

  @Test
  public void givenAddedTransaction_whenFindingByOtherThanInfractionType_shouldLookInBothAccounts() {
    TransactionType transactionType = TransactionType.PASS;
    Transaction transaction = new Transaction(AN_AMOUNT, CREATED_AT, transactionType);
    when(transactionFactory.create(transaction, transaction.getAmount().multiply(PERCENT_OF_REVENUE)))
        .thenReturn(A_TRANSACTION);
    when(transactionFactory.create(transaction, transaction.getAmount().multiply(1 - PERCENT_OF_REVENUE)))
        .thenReturn(ANOTHER_TRANSACTION);
    mainBankAccount.addTransaction(transaction);
    when(sustainableAccount.findAllTransactionsBy(transactionType)).thenReturn(Collections.singletonList(A_TRANSACTION));
    when(otherAccount.findAllTransactionsBy(transactionType)).thenReturn(Collections.singletonList(ANOTHER_TRANSACTION));

    List<Transaction> transactions = mainBankAccount.findAllBy(transactionType);

    assertThat(transactions).containsExactly(A_TRANSACTION, ANOTHER_TRANSACTION);
  }

  @Test
  public void givenAddedTransaction_whenFindingByInfractionType_shouldOnlyLookInSustainableAccount() {
    TransactionType transactionType = TransactionType.PASS;
    Transaction transaction = new Transaction(AN_AMOUNT, CREATED_AT, transactionType);
    when(transactionFactory.create(transaction, transaction.getAmount().multiply(PERCENT_OF_REVENUE)))
        .thenReturn(A_TRANSACTION);
    when(transactionFactory.create(transaction, transaction.getAmount().multiply(1 - PERCENT_OF_REVENUE)))
        .thenReturn(ANOTHER_TRANSACTION);
    mainBankAccount.addTransaction(transaction);
    when(sustainableAccount.findAllTransactionsBy(transactionType)).thenReturn(Collections.singletonList(A_TRANSACTION));

    List<Transaction> transactions = mainBankAccount.findAllBy(transactionType);

    assertThat(transactions).containsExactly(A_TRANSACTION);
  }
}
