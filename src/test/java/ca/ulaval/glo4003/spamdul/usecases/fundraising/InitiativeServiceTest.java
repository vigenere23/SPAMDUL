package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
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

  @Mock
  private InitiativeRepository initiativeRepository;
  @Mock
  private InitiativeFactory initiativeFactory;
  @Mock
  private Account sustainableMobilityProjectAccount;
  //TODO::add test to account

  @Before
  public void setUp() {
    initiativeService = new InitiativeService(initiativeRepository, initiativeFactory,
                                              sustainableMobilityProjectAccount);
  }

  @Test
  public void whenGettingAllInitiatives_shouldReturnFromRepository() {
    List<Initiative> mockedInitiatives = new ArrayList<>();
    when(initiativeRepository.findAll()).thenReturn(mockedInitiatives);

    List<Initiative> initiatives = initiativeService.getAllInitiatives();

    Truth.assertThat(initiatives).isEqualTo(mockedInitiatives);
  }

  // TODO add test for initiative creation
}
