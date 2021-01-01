package ca.ulaval.glo4003.spamdul.finance.usecases.carboncredits;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsUseCaseTest {

  private static final int A_AMOUNT_VALUE = 5;
  private static final Amount A_AMOUNT = Amount.valueOf(A_AMOUNT_VALUE);
  private static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  @Mock
  private EventSchedulerObservable eventSchedulerObservable;
  @Mock
  private InitiativeCreator initiativeCreator;
  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private SustainabilityBankAccount sustainabilityBankAccount;
  @Mock
  private CarbonCreditsTransactionService carbonCreditsTransactionService;
  @Mock
  private CarbonCreditsPurchaser carbonCreditsPurchaser;
  @Mock
  private AccessLevelValidator accessLevelValidator;

  private CarbonCreditsUseCase carbonCreditsUseCase;

  @Before
  public void setUp() {
    carbonCreditsUseCase = new CarbonCreditsUseCase(eventSchedulerObservable,
                                                    carbonCreditsPurchaser,
                                                    initiativeRepository,
                                                    initiativeCreator,
                                                    accessLevelValidator,
                                                    carbonCreditsTransactionService,
                                                    sustainabilityBankAccount);

    when(sustainabilityBankAccount.getBalance()).thenReturn(A_AMOUNT);
  }

  @Test
  public void whenActivatingAutomaticTransfer_shouldCallAccessLevelValidator() {
    carbonCreditsUseCase.activateAutomaticTransfer(true, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldRegisterToObservable() {
    carbonCreditsUseCase.activateAutomaticTransfer(true, A_TEMPORARY_TOKEN);

    verify(eventSchedulerObservable).register(carbonCreditsUseCase);
  }

  @Test
  public void whenDeactivateAutomaticTransfer_shouldUnregisterToObservable() {
    carbonCreditsUseCase.activateAutomaticTransfer(false, A_TEMPORARY_TOKEN);

    verify(eventSchedulerObservable).unregister(carbonCreditsUseCase);
  }

  @Test
  public void whenTransferringRemainingBudget_shouldAddCarbonCreditsRevenueToCarbonCreditsBankAccount() {
    carbonCreditsUseCase.transferRemainingBudget();

    verify(carbonCreditsTransactionService).addRevenue(A_AMOUNT);
  }

  @Test
  public void whenTransferRemainingBudget_shouldPurchaseWithCarbonCreditsPurchaser() {
    carbonCreditsUseCase.transferRemainingBudget();

    verify(carbonCreditsPurchaser).purchase(CarbonCredits.valueOf(A_AMOUNT));
  }
}
