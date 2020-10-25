package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class TransactionFactoryTest {

  public static final int AN_AMOUNT = 99;
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  private TransactionFactory transactionFactory;
  private TransactionDto passTransactionDto;
  private TransactionDto infractionTransactionDto;
  private TransactionDto campusAccessTransactionDto;

  @Before
  public void setUp() throws Exception {
    transactionFactory = new TransactionFactory();

    passTransactionDto = new TransactionDto();
    passTransactionDto.amount = AN_AMOUNT;
    passTransactionDto.transactionType = TransactionType.PASS;

    infractionTransactionDto = new TransactionDto();
    infractionTransactionDto.amount = AN_AMOUNT;
    infractionTransactionDto.transactionType = TransactionType.INFRACTION;

    campusAccessTransactionDto = new TransactionDto();
    campusAccessTransactionDto.amount = AN_AMOUNT;
    campusAccessTransactionDto.carType = A_CAR_TYPE;
    campusAccessTransactionDto.transactionType = TransactionType.CAMPUS_ACCESS;
  }

//  @Test
//  public void whenCreatingNewTransaction_shouldCreateTransactionOfTheRightType() {
//    Transaction passTransaction = transactionFactory.create(passTransactionDto);
//    Transaction infractionTransaction = transactionFactory.create(infractionTransactionDto);
//    Transaction campusAccessTransaction = transactionFactory.create(campusAccessTransactionDto);
//
//    Truth.assertThat(passTransaction instanceof PassTransaction).isTrue();
//    Truth.assertThat(infractionTransaction instanceof InfractionTransaction).isTrue();
//    Truth.assertThat(campusAccessTransaction instanceof CampusAccessTransaction).isTrue();
//  }
//
//  @Test(expected = CantCreateCampusAccessTransactionWithoutCarTypeException.class)
//  public void givenNoCarType_whenCreatingNewCampusAccessTransaction_shouldThrowCantCreateCampusAccessTransactionWithoutCarTypeException() {
//    campusAccessTransactionDto.carType = null;
//
//    transactionFactory.create(campusAccessTransactionDto);
//  }
}