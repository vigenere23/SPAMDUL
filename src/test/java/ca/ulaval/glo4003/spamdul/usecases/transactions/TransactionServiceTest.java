package ca.ulaval.glo4003.spamdul.usecases.transactions;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.transactions.CampusAccessTransaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transactions.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  public static final Amount AN_AMOUNT_1 = Amount.valueOf(-99);
  public static final Amount AN_AMOUNT_2 = Amount.valueOf(-127);
  public static final Amount AN_AMOUNT_3 = Amount.valueOf(-42);
  public static final TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  public static final LocalDateTime A_DATETIME = LocalDateTime.now();
  private static final TransactionDto A_TRANSACTION_DTO = new TransactionDto();
  private static final TransactionQueryDto A_TRANSACTION_QUERY_DTO = new TransactionQueryDto();
  private static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  private TransactionService transactionService;
  @Mock
  private BankRepository bankRepository;
  @Mock
  private MainBankAccount mainBankAccount;
  @Mock
  private CarbonCreditsBankAccount carbonCreditsBankAccount;
  @Mock
  private AccessLevelValidator accessLevelValidator;

  @Before
  public void setUp() {
    when(bankRepository.getMainBankAccount()).thenReturn(mainBankAccount);
    when(bankRepository.getCarbonCreditsBankAccount()).thenReturn(carbonCreditsBankAccount);
    transactionService = new TransactionService(bankRepository, accessLevelValidator);
    A_TRANSACTION_DTO.amount = AN_AMOUNT_1.asDouble();
    A_TRANSACTION_DTO.transactionType = A_TRANSACTION_TYPE;
    A_TRANSACTION_DTO.carType = A_CAR_TYPE;
  }

  @Test
  public void whenGettingCampusAccessTotalRevenue_shouldCallAccessLevelValidator() {
    transactionService.getCampusAccessTotalRevenueByCarType(A_TRANSACTION_QUERY_DTO, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingInfractionsTotalRevenue_shouldCallAccessLevelValidator() {
    transactionService.getInfractionsTotalRevenue(A_TRANSACTION_QUERY_DTO, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingPassTotalRevenue_shouldCallAccessLevelValidator() {
    transactionService.getPassTotalRevenue(A_TRANSACTION_QUERY_DTO, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingAllBoughtCreditCarbon_shouldCallAccessLevelValidator() {
    transactionService.getAllBoughtCarbonCredit(A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void givenNoInfractionTransaction_whenGetInfractionsTotalRevenue_thenReturnAmountZero() {
    when(mainBankAccount.findAllBy(TransactionType.INFRACTION)).thenReturn(Collections.emptyList());

    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue(A_TRANSACTION_QUERY_DTO,
                                                                                   A_TEMPORARY_TOKEN);

    assertThat(infractionsTotalRevenue).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenNoPassTransaction_whenGetPassTotalRevenue_thenReturnAmountZero() {
    when(mainBankAccount.findAllBy(TransactionType.PASS)).thenReturn(Collections.emptyList());

    Amount infractionsTotalRevenue = transactionService.getPassTotalRevenue(A_TRANSACTION_QUERY_DTO, A_TEMPORARY_TOKEN);

    assertThat(infractionsTotalRevenue).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenInfractionTransactionsExists_whenGetInfractionsTotalRevenue_thenReturnCorrectAmount() {
    Transaction transaction1 = new Transaction(AN_AMOUNT_1, A_DATETIME, TransactionType.INFRACTION);
    Transaction transaction2 = new Transaction(AN_AMOUNT_2, A_DATETIME, TransactionType.INFRACTION);
    when(mainBankAccount.findAllBy(TransactionType.INFRACTION)).thenReturn(Lists.newArrayList(transaction1,
                                                                                              transaction2));

    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue(A_TRANSACTION_QUERY_DTO,
                                                                                   A_TEMPORARY_TOKEN);

    assertThat(infractionsTotalRevenue).isEqualTo(AN_AMOUNT_1.add(AN_AMOUNT_2));
  }

  @Test
  public void givenPassTransactionsExists_whenGetPassTotalRevenue_thenReturnCorrectAmount() {
    Transaction transaction1 = new Transaction(AN_AMOUNT_1, A_DATETIME, TransactionType.PASS);
    Transaction transaction2 = new Transaction(AN_AMOUNT_2, A_DATETIME, TransactionType.PASS);
    when(mainBankAccount.findAllBy(TransactionType.PASS)).thenReturn(Lists.newArrayList(transaction1,
                                                                                        transaction2));

    Amount infractionsTotalRevenue = transactionService.getPassTotalRevenue(A_TRANSACTION_QUERY_DTO, A_TEMPORARY_TOKEN);

    assertThat(infractionsTotalRevenue).isEqualTo(AN_AMOUNT_1.add(AN_AMOUNT_2));
  }

  @Test
  public void givenNoCampusAccessTransactionsExistsForCarType_whenGetCampusAccessTotalRevenueByCarType_thenReturnAmountZeroForCarType() {
    when(mainBankAccount.findAllBy(TransactionType.CAMPUS_ACCESS)).thenReturn(Collections.emptyList());

    Map<CarType, Amount> revenue = transactionService.getCampusAccessTotalRevenueByCarType(A_TRANSACTION_QUERY_DTO,
                                                                                           A_TEMPORARY_TOKEN);

    assertThat(revenue.get(A_CAR_TYPE)).isEqualTo(Amount.valueOf(0));
  }

  @Test
  public void givenCampusAccessTransactionsExists_whenGetCampusAccessTotalRevenueByCarType_thenReturnCorrectAmounts() {
    Transaction transactionEconomique = new CampusAccessTransaction(AN_AMOUNT_1, A_DATETIME, CarType.ECONOMIQUE);
    Transaction transactionGourmande = new CampusAccessTransaction(AN_AMOUNT_2, A_DATETIME, CarType.GOURMANDE);
    Transaction transactionSansPollution = new CampusAccessTransaction(AN_AMOUNT_3, A_DATETIME, CarType.SANS_POLLUTION);

    when(mainBankAccount.findAllBy(TransactionType.CAMPUS_ACCESS)).thenReturn(Lists.newArrayList(
        transactionEconomique, transactionGourmande, transactionSansPollution));

    Map<CarType, Amount> revenue = transactionService.getCampusAccessTotalRevenueByCarType(A_TRANSACTION_QUERY_DTO,
                                                                                           A_TEMPORARY_TOKEN);

    assertThat(revenue.get(CarType.ECONOMIQUE)).isEqualTo(transactionEconomique.getAmount());
    assertThat(revenue.get(CarType.GOURMANDE)).isEqualTo(transactionGourmande.getAmount());
    assertThat(revenue.get(CarType.SANS_POLLUTION)).isEqualTo(transactionSansPollution.getAmount());
  }

  @Test
  public void givenNoCarbonCreditTransactionsExists_whenGetAllBoughtCarbonCredit_thenReturnAmountZero() {
    when(mainBankAccount.findAllBy(TransactionType.CARBON_CREDIT)).thenReturn(Collections.emptyList());

    CarbonCredits credits = transactionService.getAllBoughtCarbonCredit(A_TEMPORARY_TOKEN);

    assertThat(credits.asDouble()).isEqualTo(Amount.valueOf(0).asDouble());
  }

  @Test
  public void givenCarbonCreditTransactionsExists_whenGetAllBoughtCarbonCredit_thenReturnCorrectAmounts() {
    Transaction transactionCarbonCredit1 = new Transaction(AN_AMOUNT_1.multiply(-1),
                                                           A_DATETIME,
                                                           TransactionType.CARBON_CREDIT);
    Transaction transactionCarbonCredit2 = new Transaction(AN_AMOUNT_2.multiply(-1),
                                                           A_DATETIME,
                                                           TransactionType.CARBON_CREDIT);

    when(carbonCreditsBankAccount.findAll()).thenReturn(Lists.newArrayList(
        transactionCarbonCredit1, transactionCarbonCredit2));

    CarbonCredits credits = transactionService.getAllBoughtCarbonCredit(A_TEMPORARY_TOKEN);
    CarbonCredits expectedCredits = CarbonCredits.valueOf(AN_AMOUNT_1.add(AN_AMOUNT_2).multiply(-1));

    assertThat(credits.asDouble()).isEqualTo(expectedCredits.asDouble());
  }
}
