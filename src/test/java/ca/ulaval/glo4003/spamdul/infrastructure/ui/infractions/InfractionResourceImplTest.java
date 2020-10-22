package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class InfractionResourceImplTest {
  public static final String AN_INFRACTION_DESCRIPTION = "a description";
  public static final String AN_INFRACTION_CODE_STRING = "INF-01";
  public static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  public static final InfractionId A_INFRACTION_ID = new InfractionId();
  public static final int AN_AMOUNT = 99;
  private InfractionResourceImpl resource;
  private InfractionAssembler infractionAssembler;
  private InfractionService infractionService;
  private InfractionRequest infractionRequest;
  private PassToValidateDto passToValidateDto;
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
    passToValidateDto = new PassToValidateDto();
    infraction = new Infraction(A_INFRACTION_ID, AN_INFRACTION_DESCRIPTION, AN_INFRACTION_CODE, AN_AMOUNT);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapRequestToDto() {
    resource.validateParkingPass(infractionRequest);

    BDDMockito.verify(infractionAssembler, Mockito.times(1)).fromRequest(infractionRequest);
  }

  @Test
  public void whenValidatingParkingPass_shouldCallService() {
    BDDMockito.given(infractionAssembler.fromRequest(infractionRequest)).willReturn(passToValidateDto);

    resource.validateParkingPass(infractionRequest);

    BDDMockito.verify(infractionService, Mockito.times(1)).giveInfractionIfNotValid(passToValidateDto);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapToResponse() {
    BDDMockito.given(infractionAssembler.fromRequest(infractionRequest)).willReturn(passToValidateDto);
    BDDMockito.given(infractionService.giveInfractionIfNotValid(passToValidateDto)).willReturn(infraction);

    resource.validateParkingPass(infractionRequest);

    BDDMockito.verify(infractionAssembler, Mockito.times(1)).toResponse(infraction);
  }
}