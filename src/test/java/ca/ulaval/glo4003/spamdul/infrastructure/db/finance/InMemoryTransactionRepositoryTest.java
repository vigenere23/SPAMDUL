package ca.ulaval.glo4003.spamdul.infrastructure.db.finance;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TestTransactionsCreator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.utils.ListUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryTransactionRepositoryTest {

  private final TransactionType A_TRANSACTION_TYPE = TransactionType.PASS;
  private final TransactionType ANOTHER_TRANSACTION_TYPE = TransactionType.INFRACTION;

  @Mock
  private TransactionFilter transactionFilter;

  private InMemoryTransactionRepository transactionRepository;

  @Before
  public void setUp() {
    transactionRepository = new InMemoryTransactionRepository();
  }

  @Test
  public void givenInitialized_whenFindingAll_shouldReturnEmptyList() {
    List<Transaction> transactions = transactionRepository.findAll();
    assertThat(transactions).isEmpty();
  }

  @Test
  public void whenSaving_shouldSave() {
    Transaction transaction = createAndSaveTransaction(A_TRANSACTION_TYPE);
    assertThat(transactionRepository.findAll()).containsExactly(transaction);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAll_shouldReturnAllThoseTransactions() {
    Transaction transaction1 = createAndSaveTransaction(A_TRANSACTION_TYPE);
    Transaction transaction2 = createAndSaveTransaction(ANOTHER_TRANSACTION_TYPE);

    List<Transaction> transactions = transactionRepository.findAll();

    assertThat(transactions).containsExactly(transaction1, transaction2);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAllWithFilter_shouldReturnFromFilter() {
    Transaction transactionToFind = createAndSaveTransaction(A_TRANSACTION_TYPE);
    createAndSaveTransaction(ANOTHER_TRANSACTION_TYPE);
    when(transactionFilter.setData(any(List.class))).thenReturn(transactionFilter);
    when(transactionFilter.getResults()).thenReturn(ListUtil.toList(transactionToFind));

    List<Transaction> transactions = transactionRepository.findAll(transactionFilter);

    assertThat(transactions).containsExactly(transactionToFind);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAllByOneTransactionType_shouldReturnOnlyThatTransaction() {
    Transaction transactionToFind = createAndSaveTransaction(A_TRANSACTION_TYPE);
    createAndSaveTransaction(ANOTHER_TRANSACTION_TYPE);

    List<Transaction> transactions = transactionRepository.findAllBy(A_TRANSACTION_TYPE);

    assertThat(transactions).containsExactly(transactionToFind);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAllByTypeWithFilter_shouldReturnFromFilter() {
    Transaction transactionToFind = createAndSaveTransaction(A_TRANSACTION_TYPE);
    createAndSaveTransaction(ANOTHER_TRANSACTION_TYPE);
    when(transactionFilter.setData(ListUtil.toList(transactionToFind))).thenReturn(transactionFilter);
    when(transactionFilter.getResults()).thenReturn(ListUtil.toList(transactionToFind));

    List<Transaction> transactions = transactionRepository.findAllBy(A_TRANSACTION_TYPE, transactionFilter);

    assertThat(transactions).containsExactly(transactionToFind);
  }

  private Transaction createAndSaveTransaction(TransactionType transactionType) {
    Transaction transaction = TestTransactionsCreator.createSingleMock(transactionType);
    transactionRepository.save(transaction);
    return transaction;
  }
}
