package ca.ulaval.glo4003.spamdul.entity.finance;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionListTest {

  private final Amount ZERO_AMOUNT = Amount.valueOf(0);
  private final Amount AN_AMOUNT = Amount.valueOf(123.43);
  private final Amount SMALL_AMOUNT = Amount.valueOf(23.43);
  private final Amount BIG_AMOUNT = Amount.valueOf(812.34);

  @Test
  public void givenNoTransaction_whenGettingBalance_shouldReturnZero() {
    TransactionList transactionList = new TransactionList(new ArrayList<>());
    Amount balance = transactionList.getBalance();
    assertThat(balance).isEqualTo(ZERO_AMOUNT);
  }

  @Test
  public void givenASingleTransaction_whenGettingBalance_shouldReturnThatTransactionAmount() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    TransactionList transactionList = new TransactionList(transactions);

    Amount balance = transactionList.getBalance();

    assertThat(balance).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void givenMultiplePositiveTransactions_whenGettingBalance_shouldReturnPositiveSumOfTransactionAmounts() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(SMALL_AMOUNT, BIG_AMOUNT);
    TransactionList transactionList = new TransactionList(transactions);

    Amount balance = transactionList.getBalance();

    assertThat(balance).isEqualTo(SMALL_AMOUNT.add(BIG_AMOUNT));
  }

  @Test
  public void givenMultipleNegativeTransactions_whenGettingBalance_shouldReturnNegativeSumOfTransactionAmounts() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(SMALL_AMOUNT.multiply(-1),
                                                                                 BIG_AMOUNT.multiply(-1));
    TransactionList transactionList = new TransactionList(transactions);

    Amount balance = transactionList.getBalance();

    Amount expectedAmount = SMALL_AMOUNT.multiply(-1).add(BIG_AMOUNT.multiply(-1));
    assertThat(balance).isEqualTo(expectedAmount);
  }

  @Test
  public void givenOppositeTransactions_whenGettingBalance_shouldReturnZero() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(SMALL_AMOUNT,
                                                                                 SMALL_AMOUNT.multiply(-1));
    TransactionList transactionList = new TransactionList(transactions);

    Amount balance = transactionList.getBalance();

    assertThat(balance).isEqualTo(ZERO_AMOUNT);
  }
}
