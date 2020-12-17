package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import com.google.common.truth.Truth;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeUseCaseTest {

  private InitiativeUseCase initiativeUseCase;
  private final InitiativeDto A_INITIATIVE_DTO = new InitiativeDto();
  private final String A_NAME = "initiative vélo";
  private final Amount AN_AMOUNT = Amount.valueOf(510);
  private final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private Initiative initiative;
  @Mock
  private InitiativeCreator initiativeCreator;
  @Mock
  AccessLevelValidator accessLevelValidator;
  @Mock
  InitiativeDtoAssembler initiativeDtoAssembler;

  @Before
  public void setUp() {
    initiativeUseCase = new InitiativeUseCase(initiativeRepository,
                                              initiativeCreator,
                                              accessLevelValidator,
                                              initiativeDtoAssembler);
    A_INITIATIVE_DTO.amount = AN_AMOUNT;
    A_INITIATIVE_DTO.name = A_NAME;
  }

  @Test
  public void whenGettingAllInitiatives_shouldCallAccessLevelValidator() {
    initiativeUseCase.getAllInitiatives(A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGettingAllInitiatives_shouldReturnFromRepository() {
    List<Initiative> mockedInitiatives = new ArrayList<>();
    when(initiativeRepository.findAll()).thenReturn(mockedInitiatives);

    List<InitiativeDto> initiatives = initiativeUseCase.getAllInitiatives(A_TEMPORARY_TOKEN);

    Truth.assertThat(initiatives).isEqualTo(mockedInitiatives);
  }

  @Test
  public void whenAddingInitiative_shouldCallAccessLevelValidator() {
    initiativeUseCase.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void givenEnoughFunds_whenAddingInitiative_shouldSaveInitiative() {
    when(initiativeCreator.createInitiative(A_NAME, AN_AMOUNT)).thenReturn(initiative);

    initiativeUseCase.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(initiativeRepository, times(1)).save(initiative);
  }

  @Test(expected = InsufficientFundsException.class)
  public void givenNotEnoughFunds_whenAddingInitiative_shouldNotSaveInitiative() {
    doThrow(InsufficientFundsException.class).when(initiativeCreator).createInitiative(A_NAME, AN_AMOUNT);

    initiativeUseCase.addInitiative(A_INITIATIVE_DTO, A_TEMPORARY_TOKEN);

    verify(initiativeRepository, times(0)).save(initiative);
  }
}
