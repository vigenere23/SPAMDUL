package ca.ulaval.glo4003.spamdul.usecases.transaction;

import static org.mockito.Mockito.times;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.CampusAccessTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  public static final Amount AN_AMOUNT = Amount.valueOf(99);
  public static final TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  private static final TransactionDto A_TRANSACTION_DTO = new TransactionDto();
  private static final Transaction A_TRANSACTION = new CampusAccessTransaction(AN_AMOUNT, A_CAR_TYPE);
  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private TransactionFactory transactionFactory;
  private TransactionService transactionService;

  @Before
  public void setUp() {
    transactionService = new TransactionService(transactionRepository, transactionFactory);
    A_TRANSACTION_DTO.amount = AN_AMOUNT.asDouble();
    A_TRANSACTION_DTO.transactionType = A_TRANSACTION_TYPE;
    A_TRANSACTION_DTO.carType = A_CAR_TYPE;
  }

  @Test
  public void whenCreatingTransaction_shouldCreateFromFactory() {
    transactionFactory.create(A_TRANSACTION_DTO);

    BDDMockito.verify(transactionFactory, times(1)).create(A_TRANSACTION_DTO);
  }

  @Test
  public void whenCreatingTransaction_shouldSaveTransaction() {
    BDDMockito.given(transactionFactory.create(A_TRANSACTION_DTO)).willReturn(A_TRANSACTION);

    transactionService.createTransaction(A_TRANSACTION_DTO);

    BDDMockito.verify(transactionRepository, times(1)).save(A_TRANSACTION);
  }
}
