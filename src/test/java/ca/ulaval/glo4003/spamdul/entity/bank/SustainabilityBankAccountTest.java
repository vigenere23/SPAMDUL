package ca.ulaval.glo4003.spamdul.entity.bank;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SustainabilityBankAccountTest {

  private final TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  private final TransactionType ANOTHER_TRANSACTION_TYPE = TransactionType.INFRACTION;
  private final Amount AN_AMOUNT = Amount.valueOf(124.32);
  private final Amount ANOTHER_AMOUNT = Amount.valueOf(345.28);
  private final Amount A_NEGATIVE_AMOUNT = Amount.valueOf(-124.32);
  private final LocalDateTime A_DATE = LocalDateTime.now();

  private SustainabilityBankAccount sustainabilityBankAccount;

  @Before
  public void setup() {
    sustainabilityBankAccount = new SustainabilityBankAccount();
  }

  @Test
  public void givenInitializedAccount_whenFindingAllTransactionsBy_shouldReturnEmptyList() {
    List<Transaction> transactions = sustainabilityBankAccount.findAllTransactionsBy(A_TRANSACTION_TYPE);
    assertThat(transactions).isEmpty();
  }

  @Test
  public void givenAddedPositiveTransaction_whenFindingAllTransactionsByDifferentType_shouldReturnEmptyList() {
    Transaction transaction = createOfType(A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction);
    List<Transaction> transactions = sustainabilityBankAccount.findAllTransactionsBy(ANOTHER_TRANSACTION_TYPE);
    assertThat(transactions).isEmpty();
  }

  @Test
  public void givenAddedTransaction_whenFindingAllTransactionsBySameType_shouldReturnThatTransaction() {
    Transaction transaction = createOfType(A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction);
    List<Transaction> transactions = sustainabilityBankAccount.findAllTransactionsBy(A_TRANSACTION_TYPE);
    assertThat(transactions).containsExactly(transaction);
  }

  @Test
  public void givenAddedMultipleTransactions_whenFindingAllTransactionsBySameType_shouldReturnThoseTransactions() {
    Transaction transaction1 = createOfType(A_TRANSACTION_TYPE);
    Transaction transaction2 = createOfType(A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction1);
    sustainabilityBankAccount.addTransaction(transaction2);

    List<Transaction> transactions = sustainabilityBankAccount.findAllTransactionsBy(A_TRANSACTION_TYPE);

    assertThat(transactions).containsExactly(transaction1, transaction2);
  }

  @Test
  public void givenAddedMultipleDifferentTypeTransactions_whenFindingAllTransactionsByOneType_shouldReturnOneTransactions() {
    Transaction transaction = createOfType(A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction);
    sustainabilityBankAccount.addTransaction(createOfType(ANOTHER_TRANSACTION_TYPE));

    List<Transaction> transactions = sustainabilityBankAccount.findAllTransactionsBy(A_TRANSACTION_TYPE);

    assertThat(transactions).containsExactly(transaction);
  }

  @Test(expected = InsufficientFundsException.class)
  public void givenInitializedAccount_whenAddingNegativeTransaction_shouldThrowException() {
    Transaction negativeTransaction = new Transaction(A_NEGATIVE_AMOUNT, A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(negativeTransaction);
  }

  @Test(expected = InsufficientFundsException.class)
  public void givenSomeInitialFunds_whenAddingNegativeTransactionWithGreaterAmount_shouldThrowException() {
    Transaction initialFunds = new Transaction(Amount.valueOf(123.45), A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(initialFunds);

    Transaction negativeTransaction = new Transaction(Amount.valueOf(-123.46), A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(negativeTransaction);
  }

  @Test
  public void givenSomeInitialFunds_whenAddingNegativeTransactionWithSameAmount_shouldNotThrowException() {
    Transaction initialFunds = new Transaction(Amount.valueOf(123.45), A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(initialFunds);

    Transaction negativeTransaction = new Transaction(Amount.valueOf(-123.45), A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(negativeTransaction);
  }

  @Test
  public void givenInitializedAccount_whenGettingTotalAvailableAmount_shouldReturnZero() {
    Amount amount = sustainabilityBankAccount.getTotalAvailableAmount();
    assertThat(amount).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenAddedTransaction_whenGettingTotalAvailableAmount_shouldReturnAmountOfTransaction() {
    Transaction transaction = createOfType(A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction);

    Amount amount = sustainabilityBankAccount.getTotalAvailableAmount();

    assertThat(amount).isEqualTo(transaction.getAmount());
  }

  @Test
  public void givenAddedMultiplePositiveTransactions_whenGettingTotalAvailableAmount_shouldReturnSumOfAmounts() {
    Transaction transaction1 = new Transaction(AN_AMOUNT, A_DATE, A_TRANSACTION_TYPE);
    Transaction transaction2 = new Transaction(ANOTHER_AMOUNT, A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction1);
    sustainabilityBankAccount.addTransaction(transaction2);

    Amount amount = sustainabilityBankAccount.getTotalAvailableAmount();

    assertThat(amount).isEqualTo(transaction1.getAmount().add(transaction2.getAmount()));
  }

  @Test
  public void givenAddedPositiveAndNegativeTransaction_whenGettingTotalAvailableAmount_shouldReturnDifferenceOfAmounts() {
    Transaction transaction1 = new Transaction(Amount.valueOf(123.45), A_DATE, A_TRANSACTION_TYPE);
    Transaction transaction2 = new Transaction(Amount.valueOf(-12.45), A_DATE, A_TRANSACTION_TYPE);
    sustainabilityBankAccount.addTransaction(transaction1);
    sustainabilityBankAccount.addTransaction(transaction2);

    Amount amount = sustainabilityBankAccount.getTotalAvailableAmount();

    assertThat(amount).isEqualTo(transaction1.getAmount().add(transaction2.getAmount()));
  }

  @Test
  public void givenTransactionsThatCancelsEachOther_whenGettingTotalAvailableAmount_shouldReturnZero() {
    sustainabilityBankAccount.addTransaction(new Transaction(Amount.valueOf(123.45), A_DATE, A_TRANSACTION_TYPE));
    sustainabilityBankAccount.addTransaction(new Transaction(Amount.valueOf(-123.45), A_DATE, A_TRANSACTION_TYPE));

    Amount amount = sustainabilityBankAccount.getTotalAvailableAmount();

    assertThat(amount).isEqualTo(Amount.valueOf(0));
  }

  private Transaction createOfType(TransactionType transactionType) {
    return new Transaction(AN_AMOUNT, A_DATE, transactionType);
  }
}
