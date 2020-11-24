package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativesBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeServiceTest {

  private InitiativeService initiativeService;
  private final InitiativeDto A_INITIATIVE_DTO = new InitiativeDto();
  private final String A_NAME = "initiative vélo";
  private final InitiativeCode A_CODE = new InitiativeCode("ASDHJA-12");
  private final Amount AN_AMOUNT = Amount.valueOf(510);
  private final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private InitiativeFactory initiativeFactory;
  @Mock
  private Initiative initiative;
  @Mock
  private InitiativesBankAccount initiativesBankAccount;
  @Mock
  AccessLevelValidator accessLevelValidator;

  @Before
  public void setUp() {
    initiativeService = new InitiativeService(initiativeRepository,
                                              initiativeFactory,
                                              initiativesBankAccount,
                                              accessLevelValidator);
    A_INITIATIVE_DTO.amount = AN_AMOUNT;
    A_INITIATIVE_DTO.name = A_NAME;
    A_INITIATIVE_DTO.code = A_CODE;
  }

  @Test
  public void whenGettingAllInitiatives_shouldCallAccessLevelValidator() {
    initiativeService.getAllInitiatives(A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingAllInitiatives_shouldReturnFromRepository() {
    List<Initiative> mockedInitiatives = new ArrayList<>();
    when(initiativeRepository.findAll()).thenReturn(mockedInitiatives);

    List<Initiative> initiatives = initiativeService.getAllInitiatives(A_TEMPORARY_TOKEN);

    Truth.assertThat(initiatives).isEqualTo(mockedInitiatives);
  }

  @Test
  public void whenAddingInitiative_shouldCallAccessLevelValidator() {
    initiativeService.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenAddingInitiative_shouldAddExpenseToInitiativeBankAccount() {
    initiativeService.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(initiativesBankAccount, times(1)).addExpense(AN_AMOUNT);
  }

  @Test
  public void givenEnoughFunds_whenAddingInitiative_shouldSaveInitiative() {
    when(initiativeFactory.create(A_CODE, A_NAME, AN_AMOUNT)).thenReturn(initiative);

    initiativeService.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(initiativeRepository, times(1)).save(initiative);
  }

  @Test(expected = InvalidInitiativeAmount.class)
  public void givenNotEnoughFunds_whenAddingInitiative_shouldNotSaveInitiative() {
    when(initiativeFactory.create(A_CODE, A_NAME, AN_AMOUNT)).thenReturn(initiative);
    doThrow(InsufficientFundsException.class).when(initiativesBankAccount).addExpense(AN_AMOUNT);

    initiativeService.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(initiativeRepository, times(0)).save(initiative);
  }
}
