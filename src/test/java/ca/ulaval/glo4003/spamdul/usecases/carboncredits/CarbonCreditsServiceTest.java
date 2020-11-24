package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsServiceTest {

  private static final int A_AMOUNT_VALUE = 5;
  private static final Amount A_AMOUNT = Amount.valueOf(A_AMOUNT_VALUE);
  private static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  @Mock
  private EventSchedulerObservable eventSchedulerObservable;
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
  @Mock
  private AccessLevelValidator accessLevelValidator;

  private CarbonCreditsService carbonCreditsService;

  @Before
  public void setUp() {
    carbonCreditsService = new CarbonCreditsService(eventSchedulerObservable,
                                                    carbonCreditsPurchaser,
                                                    initiativeFactory,
                                                    initiativeRepository,
                                                    accessLevelValidator,
                                                    carbonCreditsBankAccount,
                                                    sustainabilityBankAccount);

    when(sustainabilityBankAccount.getBalance()).thenReturn(A_AMOUNT);
  }

  @Test
  public void whenActivatingAutomaticTransfer_shouldCallAccessLevelValidator() {
    carbonCreditsService.activateAutomaticTransfer(true, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldRegisterToObservable() {
    carbonCreditsService.activateAutomaticTransfer(true, A_TEMPORARY_TOKEN);

    verify(eventSchedulerObservable).register(carbonCreditsService);
  }

  @Test
  public void whenDeactivateAutomaticTransfer_shouldUnregisterToObservable() {
    carbonCreditsService.activateAutomaticTransfer(false, A_TEMPORARY_TOKEN);

    verify(eventSchedulerObservable).unregister(carbonCreditsService);
  }

  @Test
  public void whenTransferringRemainingBudget_shouldAddCarbonCreditsRevenueToCarbonCreditsBankAccount() {
    carbonCreditsService.transferRemainingBudget();

    verify(carbonCreditsBankAccount, times(1)).addRevenue(A_AMOUNT);
  }

  @Test
  public void whenTransferRemainingBudget_shouldPurchaseWithCarbonCreditsPurchaser() {
    carbonCreditsService.transferRemainingBudget();

    //TODO: remove any for the return value of bank account when the full method is implemented
    verify(carbonCreditsPurchaser, times(1)).purchase(CarbonCredits.valueOf(A_AMOUNT));
  }
}
