package ca.ulaval.glo4003.spamdul.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.finance.api.initiatives.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.finance.api.initiatives.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.finance.usecases.initiatives.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeAssemblerTest {

  private InitiativeAssembler initiativeAssembler;
  private InitiativeRequest initiativeRequest;
  private InitiativeDto initiative;

  private final String A_VALID_NAME = "Yolo";
  private final InitiativeCode A_VALID_CODE = InitiativeCode.valueOf("Yolo");
  private final Amount A_VALID_AMOUNT = Amount.valueOf(1234.22);

  @Before
  public void setUp() {
    initiativeAssembler = new InitiativeAssembler();
    initiativeRequest = new InitiativeRequest();
    initiativeRequest.name = A_VALID_NAME;
    initiativeRequest.amount = A_VALID_AMOUNT.asDouble();
    initiative = new InitiativeDto();
    initiative.amount = A_VALID_AMOUNT;
    initiative.code = A_VALID_CODE;
  }

  @Test
  public void givenInitiativeRequest_whenAssembling_shouldReturnEntityFromFactory() {
    InitiativeDto initiativeDto = initiativeAssembler.fromRequest(initiativeRequest);

    Truth.assertThat(initiativeDto.name).isEqualTo(initiativeRequest.name);
    Truth.assertThat(initiativeDto.amount).isEqualTo(Amount.valueOf(initiativeRequest.amount));
  }

  @Test
  public void givenInitiative_whenAssembling_shouldReturnResponse() {
    InitiativeResponse initiativeResponse = initiativeAssembler.toResponse(initiative);

    Truth.assertThat(initiativeResponse.code).isEqualTo(initiative.code.toString());
    Truth.assertThat(initiativeResponse.name).isEqualTo(initiative.name);
    Truth.assertThat(initiativeResponse.amount).isEqualTo(initiative.amount.asDouble());
  }
}
