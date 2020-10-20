package ca.ulaval.glo4003.spamdul.infrastructure.db.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.transactions.CampusAccessTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.InfractionTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.PassTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import com.google.common.truth.Truth;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InMemoryTransactionRepositoryTest {

  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  public static final CarType ANOTHER_CAR_TYPE = CarType.ECONOMIQUE;
  public static final Amount AN_AMOUNT = Amount.valueOf(99);
  private final Transaction A_CAMPUS_ACCESS_TRANSACTION = new CampusAccessTransaction(AN_AMOUNT, A_CAR_TYPE);
  private final Transaction ANOTHER_CAMPUS_ACCESS_TRANSACTION = new CampusAccessTransaction(AN_AMOUNT, A_CAR_TYPE);
  private final Transaction A_CAMPUS_ACCESS_TRANSACTION_DIFFERENT_CAR_TYPE = new CampusAccessTransaction(AN_AMOUNT,
                                                                                                         ANOTHER_CAR_TYPE);
  private final Transaction A_PASS_TRANSACTION = new PassTransaction(AN_AMOUNT);
  private final Transaction AN_INFRACTION_TRANSACTION = new InfractionTransaction(AN_AMOUNT);

  private InMemoryTransactionRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = new InMemoryTransactionRepository();
  }

  @Test
  public void whenSavingTransaction_transactionShouldBeSaved() {
    repository.save(A_PASS_TRANSACTION);
    repository.save(A_CAMPUS_ACCESS_TRANSACTION);
    repository.save(AN_INFRACTION_TRANSACTION);

    List<Transaction> campusAccessTransactions = repository.findAllBy(TransactionType.CAMPUS_ACCESS);
    Truth.assertThat(campusAccessTransactions.size()).isEqualTo(1);
    Truth.assertThat(campusAccessTransactions).contains(A_CAMPUS_ACCESS_TRANSACTION);

    List<Transaction> infractionTransactions = repository.findAllBy(TransactionType.INFRACTION);
    Truth.assertThat(infractionTransactions.size()).isEqualTo(1);
    Truth.assertThat(infractionTransactions).contains(AN_INFRACTION_TRANSACTION);

    List<Transaction> passTransactions = repository.findAllBy(TransactionType.PASS);
    Truth.assertThat(passTransactions.size()).isEqualTo(1);
    Truth.assertThat(passTransactions).contains(A_PASS_TRANSACTION);
  }

  @Test
  public void whenFindingAllTransactionByCarType_shouldReturnAllTransactions() {
    repository.save(A_CAMPUS_ACCESS_TRANSACTION);
    repository.save(ANOTHER_CAMPUS_ACCESS_TRANSACTION);
    repository.save(A_CAMPUS_ACCESS_TRANSACTION_DIFFERENT_CAR_TYPE);

    List<Transaction> carTypeTransactions = repository.findAllBy(A_CAR_TYPE);

    Truth.assertThat(carTypeTransactions.size()).isEqualTo(2);
    Truth.assertThat(carTypeTransactions).contains(A_CAMPUS_ACCESS_TRANSACTION);
    Truth.assertThat(carTypeTransactions).contains(ANOTHER_CAMPUS_ACCESS_TRANSACTION);
  }
}