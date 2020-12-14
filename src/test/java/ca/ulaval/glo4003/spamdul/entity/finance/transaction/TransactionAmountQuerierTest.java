package ca.ulaval.glo4003.spamdul.entity.finance.transaction;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.utils.ListUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionAmountQuerierTest {

  private static final Amount AN_AMOUNT = Amount.valueOf(5423.21);
  private static final Transaction A_TRANSACTION = TestTransactionsCreator.createSingleMock(
      AN_AMOUNT);
  private static final List<Transaction> SOME_TRANSACTIONS = ListUtil.toList(A_TRANSACTION);
  private static final TransactionType A_TRANSACTION_TYPE = TransactionType.INFRACTION;

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;

  private TransactionAmountQuerier queryer;

  @Before
  public void setUp() {
    queryer = new TransactionAmountQuerier(transactionRepository);
  }

  @Test
  public void whenGettingTotal_shouldReturnFromAllTransactions() {
    when(transactionRepository.findAll()).thenReturn(SOME_TRANSACTIONS);

    Amount amount = queryer.total();

    assertThat(amount).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingWithTransactionFilter_shouldReturnFromFilteredTransactions() {
    when(transactionRepository.findAll(A_TRANSACTION_FILTER)).thenReturn(SOME_TRANSACTIONS);

    Amount amount = queryer.with(A_TRANSACTION_FILTER);

    assertThat(amount).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingWithTransactionType_shouldReturnFromTypedTransactions() {
    when(transactionRepository.findAllBy(A_TRANSACTION_TYPE)).thenReturn(SOME_TRANSACTIONS);

    Amount amount = queryer.with(A_TRANSACTION_TYPE);

    assertThat(amount).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingWithTransactionFilterAndType_shouldReturnFromFilteredAndTypedTransactions() {
    when(transactionRepository.findAllBy(A_TRANSACTION_TYPE, A_TRANSACTION_FILTER)).thenReturn(SOME_TRANSACTIONS);

    Amount amount = queryer.with(A_TRANSACTION_TYPE, A_TRANSACTION_FILTER);

    assertThat(amount).isEqualTo(AN_AMOUNT);
  }
}
