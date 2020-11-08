package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.bank.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
  private InitiativeFactory initiativeFactory;
  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private CarbonCreditsBankAccount carbonCreditsBankAccount;
  @Mock
  private CarbonCreditsPurchaser carbonCreditsPurchaser;

  private CarbonCreditsService carbonCreditsService;


  @Before
  public void setUp() {
    carbonCreditsService = new CarbonCreditsService(eventSchedulerObservable,
                                                    bankRepository,
                                                    transactionFactory,
                                                    carbonCreditsPurchaser,
                                                    initiativeFactory,
                                                    initiativeRepository);

    when(bankRepository.getSustainabilityBankAccount()).thenReturn(sustainabilityBankAccount);
    when(bankRepository.getCarbonCreditsBankAccount()).thenReturn(carbonCreditsBankAccount);
    when(sustainabilityBankAccount.getTotalAvailableAmount()).thenReturn(A_AMOUNT);
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
  public void whenTransferringRemainingBudget_thenShouldGetSustainableMobilityProjectAccountFromRepository() {
    carbonCreditsService.transferRemainingBudget();

    verify(bankRepository, atLeast(1)).getSustainabilityBankAccount();
  }

  @Test
  public void whenTransferringRemainingBudget_thenShouldGetTotalSustainableMobilityAvailableAmount() {
    carbonCreditsService.transferRemainingBudget();

    verify(sustainabilityBankAccount).getTotalAvailableAmount();
  }

  @Test
  public void whenTransferringRemainingBudget_thenShouldAddTransactionToSustainableMobilityProjectAccount() {
    carbonCreditsService.transferRemainingBudget();

    verify(sustainabilityBankAccount).addTransaction(any(Transaction.class));
  }


  @Test
  public void whenTransferRemainingBudget_shouldPurchaseWithCarbonCreditsPurchaser() {
    carbonCreditsService.transferRemainingBudget();

    //TODO: remove any for the return value of bank account when the full method is implemented
    verify(carbonCreditsPurchaser).purchase(any(CarbonCredits.class));
  }
}
