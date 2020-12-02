package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TestTransactionsCreator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SustainabilityBankAccountTest {

  private final Amount AN_AMOUNT = Amount.valueOf(32.54);
  private final Amount ANOTHER_AMOUNT = Amount.valueOf(255.12);
  private final TransactionType A_TRANSACTION_TYPE = TransactionType.CARBON_CREDIT;

  @Mock
  private TransactionRepository revenueRepository;
  @Mock
  private TransactionRepository expensesRepository;
  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private Transaction A_TRANSACTION;
  @Mock
  private TransactionFilter A_TRANSACTION_FILTER;

  private SustainabilityBankAccount sustainabilityBankAccount;

  @Before
  public void setUp() {
    sustainabilityBankAccount = new SustainabilityBankAccount(transactionFactory,
                                                              revenueRepository,
                                                              expensesRepository);
  }

  @Test
  public void whenAddingRevenue_shouldCreateAndSaveTransactionToRevenueRepository() {
    when(transactionFactory.create(A_TRANSACTION_TYPE, AN_AMOUNT)).thenReturn(A_TRANSACTION);
    sustainabilityBankAccount.addRevenue(AN_AMOUNT, A_TRANSACTION_TYPE);
    verify(revenueRepository, times(1)).save(A_TRANSACTION);
  }

  @Test(expected = InsufficientFundsException.class)
  public void givenNotEnoughFunds_whenAddingExpense_shouldThrowException() {
    sustainabilityBankAccount.addExpense(AN_AMOUNT, A_TRANSACTION_TYPE);
  }

  @Test
  public void givenEnoughFunds_whenAddingExpense_shouldCreateAndSaveTransactionToExpensesRepository() {
    when(expensesRepository.findAll()).thenReturn(new ArrayList<>());
    List<Transaction> revenueTransactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueRepository.findAll()).thenReturn(revenueTransactions);
    Transaction transaction = TestTransactionsCreator.createSingleMock(AN_AMOUNT.multiply(-1));
    when(transactionFactory.create(A_TRANSACTION_TYPE, AN_AMOUNT.multiply(-1))).thenReturn(transaction);

    sustainabilityBankAccount.addExpense(AN_AMOUNT, A_TRANSACTION_TYPE);

    verify(expensesRepository, times(1)).save(transaction);
  }

  @Test
  public void whenGettingRevenue_shouldReturnFromRevenueRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueRepository.findAll()).thenReturn(transactions);

    Amount revenue = sustainabilityBankAccount.getRevenue().total();

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenueOfType_shouldReturnFromRevenueRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueRepository.findAllBy(A_TRANSACTION_TYPE)).thenReturn(transactions);

    Amount revenue = sustainabilityBankAccount.getRevenue().with(A_TRANSACTION_TYPE);

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingRevenueOfTypeWithFilter_shouldReturnFromRevenueRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(revenueRepository.findAllBy(A_TRANSACTION_TYPE, A_TRANSACTION_FILTER)).thenReturn(transactions);

    Amount revenue = sustainabilityBankAccount.getRevenue().with(A_TRANSACTION_TYPE, A_TRANSACTION_FILTER);

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingExpenses_shouldReturnFromExpensesRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(expensesRepository.findAll()).thenReturn(transactions);

    Amount revenue = sustainabilityBankAccount.getExpenses().total();

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenGettingBalance_shouldReturnRevenueMinusExpense() {
    List<Transaction> revenueTransactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    List<Transaction> expensesTransactions = TestTransactionsCreator.createMultipleMocks(ANOTHER_AMOUNT.multiply(-1));
    when(revenueRepository.findAll()).thenReturn(revenueTransactions);
    when(expensesRepository.findAll()).thenReturn(expensesTransactions);

    Amount balance = sustainabilityBankAccount.getBalance();

    assertThat(balance).isEqualTo(AN_AMOUNT.add(ANOTHER_AMOUNT.multiply(-1)));
  }
}
