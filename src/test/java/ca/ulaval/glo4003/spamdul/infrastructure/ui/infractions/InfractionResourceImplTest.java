package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionValidationDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class InfractionResourceImplTest {

  private InfractionResourceImpl resource;
  private InfractionAssembler infractionAssembler;
  private InfractionService infractionService;
  private InfractionRequest infractionRequest;
  private InfractionValidationDto infractionValidationDto;
  private Infraction infraction;

  @Before
  public void setUp() throws Exception {
    infractionAssembler = BDDMockito.mock(InfractionAssembler.class);
    infractionService = BDDMockito.mock(InfractionService.class);
    resource = new InfractionResourceImpl(infractionAssembler, infractionService);
    infractionRequest = new InfractionRequest();
    infractionRequest.parkingZone = "Zone1";
    infractionRequest.passCode = "1";
    infractionRequest.timeOfTheDay = "12h00";
    infractionValidationDto = new InfractionValidationDto();
    infraction = new Infraction("infraction", InfractionCode.valueOf("a1"), 22);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapRequestToDto() {
    resource.validateParkingPass(infractionRequest);

    BDDMockito.verify(infractionAssembler, Mockito.times(1)).fromRequest(infractionRequest);
  }

  @Test
  public void whenValidatingParkingPass_shouldCallService() {
    BDDMockito.given(infractionAssembler.fromRequest(infractionRequest)).willReturn(infractionValidationDto);

    resource.validateParkingPass(infractionRequest);

    BDDMockito.verify(infractionService, Mockito.times(1)).validatePass(infractionValidationDto);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapToResponse() {
    BDDMockito.given(infractionAssembler.fromRequest(infractionRequest)).willReturn(infractionValidationDto);
    BDDMockito.given(infractionService.validatePass(infractionValidationDto)).willReturn(infraction);

    resource.validateParkingPass(infractionRequest);

    BDDMockito.verify(infractionAssembler, Mockito.times(1)).toResponse(infraction);
  }
}