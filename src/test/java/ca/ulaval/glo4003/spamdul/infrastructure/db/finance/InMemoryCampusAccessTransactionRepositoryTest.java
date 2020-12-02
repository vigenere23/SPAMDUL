package ca.ulaval.glo4003.spamdul.infrastructure.db.finance;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TestTransactionsCreator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.ListUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryCampusAccessTransactionRepositoryTest {

  private final CarType A_CAR_TYPE = CarType.GOURMANDE;
  private final CarType ANOTHER_CAR_TYPE = CarType.ECONOMIQUE;

  @Mock
  private TransactionFilter transactionFilter;

  private InMemoryCampusAccessTransactionRepository transactionRepository;

  @Before
  public void setUp() {
    transactionRepository = new InMemoryCampusAccessTransactionRepository();
  }

  @Test
  public void givenInitialized_whenFindingAll_shouldReturnEmptyList() {
    List<Transaction> transactions = transactionRepository.findAll();
    assertThat(transactions).isEmpty();
  }

  @Test
  public void whenSaving_shouldSave() {
    Transaction transaction = createAndSaveTransaction(A_CAR_TYPE);
    assertThat(transactionRepository.findAll()).containsExactly(transaction);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAll_shouldReturnAllThoseTransactions() {
    Transaction transaction1 = createAndSaveTransaction(A_CAR_TYPE);
    Transaction transaction2 = createAndSaveTransaction(ANOTHER_CAR_TYPE);

    List<Transaction> transactions = transactionRepository.findAll();

    assertThat(transactions).containsExactly(transaction1, transaction2);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAllByOneCarType_shouldReturnOnlyThatTransaction() {
    Transaction transactionToFind = createAndSaveTransaction(A_CAR_TYPE);
    createAndSaveTransaction(ANOTHER_CAR_TYPE);

    List<Transaction> transactions = transactionRepository.findAllBy(A_CAR_TYPE);

    assertThat(transactions).containsExactly(transactionToFind);
  }

  @Test
  public void givenTransactionsSaved_whenFindingAllByCarTypeWithFilter_shouldReturnFromFilter() {
    Transaction transactionToFind = createAndSaveTransaction(A_CAR_TYPE);
    createAndSaveTransaction(ANOTHER_CAR_TYPE);
    when(transactionFilter.setData(ListUtil.toList(transactionToFind))).thenReturn(transactionFilter);
    when(transactionFilter.getResults()).thenReturn(ListUtil.toList(transactionToFind));

    List<Transaction> transactions = transactionRepository.findAllBy(A_CAR_TYPE, transactionFilter);

    assertThat(transactions).containsExactly(transactionToFind);
  }

  private Transaction createAndSaveTransaction(CarType carType) {
    Transaction transaction = TestTransactionsCreator.createSingleMock(TransactionType.INFRACTION);
    transactionRepository.save(transaction, carType);
    return transaction;
  }
}
