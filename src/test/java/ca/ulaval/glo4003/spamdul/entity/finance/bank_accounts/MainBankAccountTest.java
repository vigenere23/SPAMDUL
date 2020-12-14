package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TestTransactionsCreator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainBankAccountTest {

  private final Amount AN_AMOUNT = Amount.valueOf(823.98);
  private final TransactionType A_TRANSACTION_TYPE = TransactionType.INITIATIVE;

  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private TransactionRepository revenueTransactionRepository;
  @Mock
  private TransactionRepository expensesTransactionRepository;
  @Mock
  private Transaction A_TRANSACTION;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;

  private MainBankAccount mainBankAccount;

  @Before
  public void setUp() {
    mainBankAccount = new MainBankAccount(transactionFactory,
                                          revenueTransactionRepository,
                                          expensesTransactionRepository);
  }

  @Test
  public void whenAddingRevenue_shouldCreateAndSaveTransactionToRepo() {
    when(transactionFactory.create(A_TRANSACTION_TYPE, AN_AMOUNT)).thenReturn(A_TRANSACTION);

    mainBankAccount.addRevenue(AN_AMOUNT, A_TRANSACTION_TYPE);

    verify(revenueTransactionRepository, times(1)).save(A_TRANSACTION);
  }

  @Test
  public void whenGettingRevenue_shouldReturnFromRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueTransactionRepository.findAll()).thenReturn(transactions);

    Amount revenue = mainBankAccount.getRevenue().total();

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenueOfType_shouldReturnFromRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueTransactionRepository.findAllBy(A_TRANSACTION_TYPE)).thenReturn(transactions);

    Amount revenue = mainBankAccount.getRevenue().with(A_TRANSACTION_TYPE);

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenueOfTypeWithFilter_shouldReturnFromRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueTransactionRepository.findAllBy(A_TRANSACTION_TYPE, A_TRANSACTION_FILTER)).thenReturn(transactions);

    Amount revenue = mainBankAccount.getRevenue().with(A_TRANSACTION_TYPE, A_TRANSACTION_FILTER);

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }
}
