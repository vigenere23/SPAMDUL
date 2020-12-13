package ca.ulaval.glo4003.spamdul.entity.finance.transaction_services;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TestTransactionsCreator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessTransactionServiceTest {

  private final Amount AN_AMOUNT = Amount.valueOf(213.34);
  private final CarType A_CAR_TYPE = CarType.GOURMANDE;

  @Mock
  private MainBankAccount mainBankAccount;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private CampusAccessTransactionRepository campusAccessTransactionRepository;
  @Mock
  private Transaction A_TRANSACTION;
  @Mock
  private Transaction ANOTHER_TRANSACTION;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;

  private CampusAccessTransactionService campusAccessTransactionService;

  @Before
  public void setUp() {
    campusAccessTransactionService = new CampusAccessTransactionService(mainBankAccount,
                                                                        sustainabilityBankAccount,
                                                                        campusAccessTransactionRepository);
  }

  @Test
  public void whenAddingRevenue_shouldAdd40PercentToSustainabilityRevenue() {
    campusAccessTransactionService.addRevenue(AN_AMOUNT, A_CAR_TYPE);
    verify(sustainabilityBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.4), TransactionType.CAMPUS_ACCESS);
  }

  @Test
  public void whenAddingRevenue_shouldAdd60PercentToMainRevenue() {
    campusAccessTransactionService.addRevenue(AN_AMOUNT, A_CAR_TYPE);
    verify(mainBankAccount, times(1)).addRevenue(AN_AMOUNT.multiply(0.6), TransactionType.CAMPUS_ACCESS);
  }

  @Test
  public void whenAddingRevenue_shouldSaveBothAccountTransactionsToRepository() {
    when(mainBankAccount.addRevenue(any(Amount.class), any(TransactionType.class))).thenReturn(A_TRANSACTION);
    when(sustainabilityBankAccount.addRevenue(any(Amount.class), any(TransactionType.class))).thenReturn(
        ANOTHER_TRANSACTION);

    campusAccessTransactionService.addRevenue(AN_AMOUNT, A_CAR_TYPE);

    verify(campusAccessTransactionRepository, times(1)).save(A_TRANSACTION, A_CAR_TYPE);
    verify(campusAccessTransactionRepository, times(1)).save(ANOTHER_TRANSACTION, A_CAR_TYPE);
  }

  @Test
  public void whenGettingRevenue_returnsFromRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(campusAccessTransactionRepository.findAll()).thenReturn(transactions);

    Amount revenue = campusAccessTransactionService.getRevenue();

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenueWithCarTypeAndFilter_returnsFromRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(campusAccessTransactionRepository.findAllBy(A_CAR_TYPE, A_TRANSACTION_FILTER)).thenReturn(transactions);

    Amount revenue = campusAccessTransactionService.getRevenue(A_CAR_TYPE, A_TRANSACTION_FILTER);

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }
}
