package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsServiceTest {

  private static final int A_AMOUNT_VALUE = 5;
  private static final Amount A_AMOUNT = Amount.valueOf(A_AMOUNT_VALUE);

  @Mock
  private EventSchedulerObservable eventSchedulerObservable;
  @Mock
  private BankRepository bankRepository;
  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private Account account;

  private CarbonCreditsService carbonCreditsService;


  @Before
  public void setUp() {
    carbonCreditsService = new CarbonCreditsService(eventSchedulerObservable, bankRepository, transactionFactory);

    willReturn(account).given(bankRepository).getSustainableMobilityProjectAccount();
    willReturn(A_AMOUNT).given(account).getTotalAvailableAmount();
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldRegisterToObservable() {
    carbonCreditsService.activateAutomaticTransfer(true);

    verify(eventSchedulerObservable).register(carbonCreditsService);
  }

  @Test
  public void whenDeactivateAutomaticTransfer_shouldUnregisterToObservable() {
    carbonCreditsService.activateAutomaticTransfer(false);

    verify(eventSchedulerObservable).unregister(carbonCreditsService);
  }

  @Test
  public void whenTransferringRemainingBudget_thenShouldGetSustainableMobilityProjectAccountFromRepository(){
    carbonCreditsService.transferRemainingBudget();

    verify(bankRepository, atLeast(1)).getSustainableMobilityProjectAccount();
  }

  @Test
  public void whenTransferringRemainingBudget_thenShouldGetTotalSustainableMobilityAvailableAmount(){
    carbonCreditsService.transferRemainingBudget();

    verify(account).getTotalAvailableAmount();
  }

  @Test
  public void whenTransferringRemainingBudget_thenShouldAddTransactionToSustainableMobilityProjectAccount(){
    carbonCreditsService.transferRemainingBudget();

    verify(account).addTransaction(any(Transaction.class));
  }
}
