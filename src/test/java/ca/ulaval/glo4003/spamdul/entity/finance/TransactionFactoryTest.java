package ca.ulaval.glo4003.spamdul.entity.finance;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class TransactionFactoryTest {

  private final Amount AN_AMOUNT = Amount.valueOf(452.12);
  private final TransactionType A_TRANSACTION_TYPE = TransactionType.CARBON_CREDIT;

  private TransactionFactory transactionFactory;

  @Before
  public void setUp() {
    transactionFactory = new TransactionFactory();
  }

  @Test
  public void whenCreating_shouldSetCreatedAtToNow() {
    LocalDateTime aBitBeforeNow = LocalDateTime.now();
    Transaction transaction = transactionFactory.create(A_TRANSACTION_TYPE, AN_AMOUNT);
    LocalDateTime aBitAfterNow = LocalDateTime.now();
    
    assertThat(transaction.getCreatedAt()).isAtLeast(aBitBeforeNow);
    assertThat(transaction.getCreatedAt()).isAtMost(aBitAfterNow);
  }

  @Test
  public void givenAmount_whenCreating_shouldHaveAmount() {
    Transaction transaction = transactionFactory.create(A_TRANSACTION_TYPE, AN_AMOUNT);
    assertThat(transaction.getAmount()).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void givenTransactionType_whenCreating_shouldHaveTransactionType() {
    Transaction transaction = transactionFactory.create(A_TRANSACTION_TYPE, AN_AMOUNT);
    assertThat(transaction.getTransactionType()).isEqualTo(A_TRANSACTION_TYPE);
  }
}
