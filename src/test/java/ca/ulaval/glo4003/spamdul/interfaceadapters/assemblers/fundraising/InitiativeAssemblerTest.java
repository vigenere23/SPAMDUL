package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising;

import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeAssemblerTest {

  private InitiativeAssembler initiativeAssembler;
  private InitiativeRequest initiativeRequest;
  private Initiative initiative;

  @Mock
  private InitiativeFactory initiativeFactory;
  @Mock
  private Initiative AN_INITIATIVE;

  private final String A_VALID_NAME = "Yolo";
  private final double A_VALID_AMOUNT = 1234.22;
  private final InitiativeId AN_ID = new InitiativeId();

  @Before
  public void setUp() {
    initiativeAssembler = new InitiativeAssembler(initiativeFactory);
    initiativeRequest = new InitiativeRequest();
    initiativeRequest.name = A_VALID_NAME;
    initiativeRequest.amount = A_VALID_AMOUNT;
    initiative = new Initiative(AN_ID, A_VALID_NAME, A_VALID_AMOUNT);
  }

  @Test
  public void givenInitiativeRequest_whenAssembling_shouldReturnEntityFromFactory() {
    when(initiativeFactory.create(initiativeRequest.name, initiativeRequest.amount)).thenReturn(AN_INITIATIVE);
    Initiative initiative = initiativeAssembler.fromRequest(initiativeRequest);
    Truth.assertThat(initiative).isEqualTo(AN_INITIATIVE);
  }

  @Test
  public void givenInitiative_whenAssembling_shouldReturnResponse() {
    InitiativeResponse initiativeResponse = initiativeAssembler.toResponse(initiative);
    Truth.assertThat(initiativeResponse.id).isEqualTo(initiative.getId().toString());
    Truth.assertThat(initiativeResponse.name).isEqualTo(initiative.getName());
    Truth.assertThat(initiativeResponse.amount).isEqualTo(initiative.getAmount());
  }
}
