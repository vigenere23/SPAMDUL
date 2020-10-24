package ca.ulaval.glo4003.spamdul.usecases.transaction;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.CampusAccessTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.InfractionTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.PassTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.banking.BankingService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  public static final Amount AN_AMOUNT_1 = Amount.valueOf(99);
  public static final Amount AN_AMOUNT_2 = Amount.valueOf(127);
  public static final Amount AN_AMOUNT_3 = Amount.valueOf(42);
  public static final TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  private static final TransactionDto A_TRANSACTION_DTO = new TransactionDto();
  private static final Transaction A_TRANSACTION = new CampusAccessTransaction(AN_AMOUNT_1, A_CAR_TYPE);
  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private TransactionFactory transactionFactory;
  private TransactionService transactionService;
  @Mock
  private BankingService bankingService;
  //TODO::add test to account

  @Before
  public void setUp() {
    transactionService = new TransactionService(transactionRepository, transactionFactory);
    A_TRANSACTION_DTO.amount = AN_AMOUNT_1.asDouble();
    A_TRANSACTION_DTO.transactionType = A_TRANSACTION_TYPE;
    A_TRANSACTION_DTO.carType = A_CAR_TYPE;
  }

  @Test
  public void whenCreatingTransaction_shouldCreateFromFactory() {
    transactionFactory.create(A_TRANSACTION_DTO);

    verify(transactionFactory, times(1)).create(A_TRANSACTION_DTO);
  }

  @Test
  public void whenCreatingTransaction_shouldSaveTransaction() {
    given(transactionFactory.create(A_TRANSACTION_DTO)).willReturn(A_TRANSACTION);

    transactionService.createTransaction(A_TRANSACTION_DTO);

    verify(transactionRepository, times(1)).save(A_TRANSACTION);
  }

  @Test
  public void givenNoInfractionTransaction_whenGetInfractionsTotalRevenue_thenReturnAmountZero() {
    given(transactionRepository.findAllBy(TransactionType.INFRACTION)).willReturn(Collections.emptyList());

    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue();

    assertThat(infractionsTotalRevenue).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenNoPassTransaction_whenGetPassTotalRevenue_thenReturnAmountZero() {
    given(transactionRepository.findAllBy(TransactionType.PASS)).willReturn(Collections.emptyList());

    Amount infractionsTotalRevenue = transactionService.getPassTotalRevenue();

    assertThat(infractionsTotalRevenue).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenInfractionTransactionsExists_whenGetInfractionsTotalRevenue_thenReturnCorrectAmount() {
    Transaction transaction1 = new InfractionTransaction(AN_AMOUNT_1);
    Transaction transaction2 = new InfractionTransaction(AN_AMOUNT_2);
    given(transactionRepository.findAllBy(TransactionType.INFRACTION)).willReturn(Lists.newArrayList(transaction1,
                                                                                                     transaction2));

    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue();

    assertThat(infractionsTotalRevenue).isEqualTo(AN_AMOUNT_1.add(AN_AMOUNT_2));
  }

  @Test
  public void givenPassTransactionsExists_whenGetPassTotalRevenue_thenReturnCorrectAmount() {
    Transaction transaction1 = new PassTransaction(AN_AMOUNT_1);
    Transaction transaction2 = new PassTransaction(AN_AMOUNT_2);
    given(transactionRepository.findAllBy(TransactionType.PASS)).willReturn(Lists.newArrayList(transaction1,
                                                                                               transaction2));

    Amount infractionsTotalRevenue = transactionService.getPassTotalRevenue();

    assertThat(infractionsTotalRevenue).isEqualTo(AN_AMOUNT_1.add(AN_AMOUNT_2));
  }

  @Test
  public void givenNoCampusAccessTransactionsExistsForCarType_whenGetCampusAccessTotalRevenueByCarType_thenReturnAmountZeroForCarType() {
    given(transactionRepository.findAllBy(A_CAR_TYPE)).willReturn(Collections.emptyList());

    Map<CarType, Amount> revenue = transactionService.getTotalCampusAccessRevenueByCarType();

    assertThat(revenue.get(A_CAR_TYPE)).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenCampusAccessTransactionsExists_whenGetCampusAccessTotalRevenueByCarType_thenReturnCorrectAmounts() {
    Transaction transactionEconomique = new CampusAccessTransaction(AN_AMOUNT_1, CarType.ECONOMIQUE);
    Transaction transactionGourmande = new CampusAccessTransaction(AN_AMOUNT_2, CarType.GOURMANDE);
    Transaction transactionSansPollution = new CampusAccessTransaction(AN_AMOUNT_3, CarType.SANS_POLLUTION);

    given(transactionRepository.findAllBy(CarType.ECONOMIQUE)).willReturn(Lists.newArrayList(transactionEconomique));
    given(transactionRepository.findAllBy(CarType.GOURMANDE)).willReturn(Lists.newArrayList(transactionGourmande));
    given(transactionRepository.findAllBy(CarType.SANS_POLLUTION)).willReturn(Lists.newArrayList(transactionSansPollution));

    Map<CarType, Amount> revenue = transactionService.getTotalCampusAccessRevenueByCarType();

    assertThat(revenue.get(CarType.ECONOMIQUE)).isEqualTo(transactionEconomique.getAmount());
    assertThat(revenue.get(CarType.GOURMANDE)).isEqualTo(transactionGourmande.getAmount());
    assertThat(revenue.get(CarType.SANS_POLLUTION)).isEqualTo(transactionSansPollution.getAmount());
  }
}
