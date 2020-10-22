package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class InfractionResourceImplTest {
  public static final String AN_INFRACTION_DESCRIPTION = "a description";
  public static final String AN_INFRACTION_CODE_STRING = "INF-01";
  public static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  public static final InfractionId A_INFRACTION_ID = new InfractionId();
  public static final int AN_AMOUNT = 99;
  public static final InfractionResponse AN_INFRACTION_RESPONSE = new InfractionResponse();
  public static final InfractionPaymentRequest AN_INFRACTION_PAYMENT_REQUEST = new InfractionPaymentRequest();
  public static final InfractionPaymentDto AN_INFRACTION_PAYMENT_DTO = new InfractionPaymentDto();
  private InfractionResourceImpl resource;
  private InfractionAssembler infractionAssembler;
  private InfractionService infractionService;
  private InfractionRequest infractionRequest;
  private PassToValidateDto passToValidateDto;
  private Infraction infraction;

  @Before
  public void setUp() throws Exception {
    infractionAssembler = mock(InfractionAssembler.class);
    infractionService = mock(InfractionService.class);
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

    verify(infractionAssembler, Mockito.times(1)).fromRequest(infractionRequest);
  }

  @Test
  public void whenValidatingParkingPass_shouldCallService() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);

    resource.validateParkingPass(infractionRequest);

    verify(infractionService, Mockito.times(1)).giveInfractionIfNotValid(passToValidateDto);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapToResponse() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionService.giveInfractionIfNotValid(passToValidateDto)).thenReturn(infraction);

    resource.validateParkingPass(infractionRequest);

    verify(infractionAssembler, Mockito.times(1)).toResponse(infraction);
  }

  @Test
  public void givenNullResponse_whenValidatingParkingPass_shouldReturn204() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionService.giveInfractionIfNotValid(passToValidateDto)).thenReturn(infraction);
    when(infractionAssembler.toResponse(infraction)).thenReturn(null);

    Response response = resource.validateParkingPass(infractionRequest);

    assertThat(response.getStatus()).isEqualTo(204);
    assertThat(response.getEntity()).isNull();
  }

  @Test
  public void givenNotNullResponse_whenValidatingParkingPass_shouldReturn200WithResponseAsBody() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionService.giveInfractionIfNotValid(passToValidateDto)).thenReturn(infraction);
    when(infractionAssembler.toResponse(infraction)).thenReturn(AN_INFRACTION_RESPONSE);

    Response response = resource.validateParkingPass(infractionRequest);

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(response.getEntity()).isEqualTo(AN_INFRACTION_RESPONSE);
  }

  @Test
  public void whenPayingInfraction_shouldAssembleDto() {
    resource.payInfraction(AN_INFRACTION_PAYMENT_REQUEST);

    verify(infractionAssembler).fromRequest(AN_INFRACTION_PAYMENT_REQUEST);
  }

  @Test
  public void whenPayingInfraction_shouldAskServiceToPayInfraction() {
    when(infractionAssembler.fromRequest(AN_INFRACTION_PAYMENT_REQUEST)).thenReturn(AN_INFRACTION_PAYMENT_DTO);

    resource.payInfraction(AN_INFRACTION_PAYMENT_REQUEST);

    verify(infractionService).payInfraction(AN_INFRACTION_PAYMENT_DTO);
  }

  @Test
  public void whenPayingInfraction_shouldReturn200() {
    when(infractionAssembler.fromRequest(AN_INFRACTION_PAYMENT_REQUEST)).thenReturn(AN_INFRACTION_PAYMENT_DTO);

    Response response = resource.payInfraction(AN_INFRACTION_PAYMENT_REQUEST);

    assertThat(response.getStatus()).isEqualTo(200);
  }
}