package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionResourceImplTest {

  public static final String AN_INFRACTION_DESCRIPTION = "a description";
  public static final String AN_INFRACTION_CODE_STRING = "INF-01";
  public static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  public static final InfractionId A_INFRACTION_ID = new InfractionId();
  public static final Amount AN_AMOUNT = Amount.valueOf(99);
  public static final InfractionResponse AN_INFRACTION_RESPONSE = new InfractionResponse();
  public static final InfractionPaymentRequest AN_INFRACTION_PAYMENT_REQUEST = new InfractionPaymentRequest();
  public static final InfractionPaymentDto AN_INFRACTION_PAYMENT_DTO = new InfractionPaymentDto();
  public static final String TOKEN_CODE = "token_code";
  public static final Cookie A_COOKIE = new Cookie("accessToken", TOKEN_CODE);
  public static final TemporaryToken A_TEMPORARY_TOKEN = TemporaryToken.valueOf(TOKEN_CODE);

  private InfractionResourceImpl resource;
  @Mock
  private InfractionAssembler infractionAssembler;
  @Mock
  private InfractionService infractionService;
  private InfractionRequest infractionRequest;
  private PassToValidateDto passToValidateDto;
  private Infraction infraction;
  private AccessTokenCookieAssembler cookieAssembler;

  @Before
  public void setUp() throws Exception {
    cookieAssembler = new AccessTokenCookieAssembler();
    resource = new InfractionResourceImpl(infractionAssembler, infractionService, cookieAssembler);
    infractionRequest = new InfractionRequest();
    infractionRequest.parkingZone = "Zone1";
    infractionRequest.passCode = "1";
    passToValidateDto = new PassToValidateDto();
    infraction = new Infraction(A_INFRACTION_ID, AN_INFRACTION_DESCRIPTION, AN_INFRACTION_CODE, AN_AMOUNT);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapRequestToDto() {
    resource.validateParkingPass(infractionRequest, A_COOKIE);

    verify(infractionAssembler, Mockito.times(1)).fromRequest(infractionRequest);
  }

  @Test
  public void whenValidatingParkingPass_shouldCallService() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);

    resource.validateParkingPass(infractionRequest, A_COOKIE);

    verify(infractionService, Mockito.times(1)).giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapToResponse() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN)).thenReturn(infraction);

    resource.validateParkingPass(infractionRequest, A_COOKIE);

    verify(infractionAssembler, Mockito.times(1)).toResponse(infraction);
  }

  @Test
  public void givenNullResponse_whenValidatingParkingPass_shouldReturn204() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN)).thenReturn(infraction);
    when(infractionAssembler.toResponse(infraction)).thenReturn(null);

    Response response = resource.validateParkingPass(infractionRequest, A_COOKIE);

    assertThat(response.getStatus()).isEqualTo(204);
    assertThat(response.getEntity()).isNull();
  }

  @Test
  public void givenNotNullResponse_whenValidatingParkingPass_shouldReturn200WithResponseAsBody() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN)).thenReturn(infraction);
    when(infractionAssembler.toResponse(infraction)).thenReturn(AN_INFRACTION_RESPONSE);

    Response response = resource.validateParkingPass(infractionRequest, A_COOKIE);

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