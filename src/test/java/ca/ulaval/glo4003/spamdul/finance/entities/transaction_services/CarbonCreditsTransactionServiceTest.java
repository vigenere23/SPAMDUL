package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TestTransactionsCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsTransactionServiceTest {

  private final Amount AN_AMOUNT = Amount.valueOf(123.43);

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private Transaction A_TRANSACTION;

  private CarbonCreditsTransactionService carbonCreditsTransactionService;

  @Before
  public void setUp() {
    carbonCreditsTransactionService = new CarbonCreditsTransactionService(transactionFactory, transactionRepository);
  }

  @Test
  public void whenAddingRevenue_shouldCreateAndSaveTransactionToRepo() {
    when(transactionFactory.create(TransactionType.CARBON_CREDIT, AN_AMOUNT)).thenReturn(A_TRANSACTION);

    carbonCreditsTransactionService.addRevenue(AN_AMOUNT);

    verify(transactionRepository, times(1)).save(A_TRANSACTION);
  }

  @Test
  public void whenGettingRevenue_shouldReturnFromRepository() {
    List<Transaction> transactions = TestTransactionsCreator.createMultipleMocks(AN_AMOUNT);
    when(transactionRepository.findAll()).thenReturn(transactions);

    Amount revenue = carbonCreditsTransactionService.getRevenue();

    assertThat(revenue).isEqualTo(AN_AMOUNT);
  }
}
