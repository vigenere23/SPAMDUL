package ca.ulaval.glo4003.spamdul.entity.transactions;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import org.junit.Before;
import org.junit.Test;

public class TransactionFactoryTest {

  public static final int AN_AMOUNT = 99;
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;

  private TransactionFactory transactionFactory;
  private TransactionDto a_transactionDto;
  private TransactionDto campusAccessTransactionDto;

  @Before
  public void setUp() throws Exception {
    transactionFactory = new TransactionFactory();

    a_transactionDto = new TransactionDto();
    a_transactionDto.amount = AN_AMOUNT;
    a_transactionDto.transactionType = TransactionType.INFRACTION;

    campusAccessTransactionDto = new TransactionDto();
    campusAccessTransactionDto.amount = AN_AMOUNT;
    campusAccessTransactionDto.carType = A_CAR_TYPE;
    campusAccessTransactionDto.transactionType = TransactionType.CAMPUS_ACCESS;
  }

  @Test
  public void givenACampusAccessTransactionType_shouldReturnTransactionOfTypeCampusAccess() {
    Transaction transaction = transactionFactory.create(campusAccessTransactionDto);

    assertThat(transaction instanceof CampusAccessTransaction).isTrue();
  }

  @Test(expected = CantCreateCampusAccessTransactionWithoutCarTypeException.class)
  public void givenACampusAccessTransactionTypeWithoutCarType_whenCreatingTransaction_shouldThrowCantCreateTransactionException() {
    campusAccessTransactionDto.carType = null;
    transactionFactory.create(campusAccessTransactionDto);
  }

  @Test
  public void whenCreatingTransaction_shouldCreateTransactionWithTheRightInfos() {
    Transaction transaction = transactionFactory.create(a_transactionDto);

    assertThat(transaction.getAmount().asDouble()).isEqualTo(a_transactionDto.amount);
    assertThat(transaction.getTransactionType()).isEqualTo(a_transactionDto.transactionType);
  }
}