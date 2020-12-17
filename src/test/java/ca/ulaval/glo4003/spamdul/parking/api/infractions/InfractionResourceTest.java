package ca.ulaval.glo4003.spamdul.parking.api.infractions;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.InfractionUseCase;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.dto.InfractionDto;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionResourceTest {

  public static final String AN_INFRACTION_DESCRIPTION = "a description";
  public static final String AN_INFRACTION_CODE_STRING = "INF-01";
  public static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_STRING);
  public static final InfractionId A_INFRACTION_ID = InfractionId.valueOf("123");
  public static final Amount AN_AMOUNT = Amount.valueOf(99);
  public static final InfractionResponse AN_INFRACTION_RESPONSE = new InfractionResponse();
  public static final InfractionPaymentRequest AN_INFRACTION_PAYMENT_REQUEST = new InfractionPaymentRequest();
  public static final InfractionPaymentDto AN_INFRACTION_PAYMENT_DTO = new InfractionPaymentDto();
  public static final String TOKEN_CODE = "token_code";
  public static final Cookie A_COOKIE = new Cookie("accessToken", TOKEN_CODE);
  public static final TemporaryToken A_TEMPORARY_TOKEN = TemporaryToken.valueOf(TOKEN_CODE);

  private InfractionResource resource;
  @Mock
  private InfractionAssembler infractionAssembler;
  @Mock
  private InfractionUseCase infractionUseCase;
  private InfractionRequest infractionRequest;
  private PassToValidateDto passToValidateDto;
  private InfractionDto infractionDto;
  private AccessTokenCookieAssembler cookieAssembler;

  @Before
  public void setUp() throws Exception {
    cookieAssembler = new AccessTokenCookieAssembler();
    resource = new InfractionResource(infractionAssembler, infractionUseCase, cookieAssembler);
    infractionRequest = new InfractionRequest();
    infractionRequest.parkingZone = "Zone1";
    infractionRequest.passCode = "1";
    passToValidateDto = new PassToValidateDto();
    infractionDto = new InfractionDto();
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

    verify(infractionUseCase, Mockito.times(1)).giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenValidatingParkingPass_shouldMapToResponse() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN)).thenReturn(infractionDto);

    resource.validateParkingPass(infractionRequest, A_COOKIE);

    verify(infractionAssembler, Mockito.times(1)).toResponse(infractionDto);
  }

  @Test
  public void givenNullResponse_whenValidatingParkingPass_shouldReturn204() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN)).thenReturn(infractionDto);
    when(infractionAssembler.toResponse(infractionDto)).thenReturn(null);

    Response response = resource.validateParkingPass(infractionRequest, A_COOKIE);

    assertThat(response.getStatus()).isEqualTo(204);
    assertThat(response.getEntity()).isNull();
  }

  @Test
  public void givenNotNullResponse_whenValidatingParkingPass_shouldReturn200WithResponseAsBody() {
    when(infractionAssembler.fromRequest(infractionRequest)).thenReturn(passToValidateDto);
    when(infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN)).thenReturn(infractionDto);
    when(infractionAssembler.toResponse(infractionDto)).thenReturn(AN_INFRACTION_RESPONSE);

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

    verify(infractionUseCase).payInfraction(AN_INFRACTION_PAYMENT_DTO);
  }

  @Test
  public void whenPayingInfraction_shouldReturn200() {
    when(infractionAssembler.fromRequest(AN_INFRACTION_PAYMENT_REQUEST)).thenReturn(AN_INFRACTION_PAYMENT_DTO);

    Response response = resource.payInfraction(AN_INFRACTION_PAYMENT_REQUEST);

    assertThat(response.getStatus()).isEqualTo(200);
  }
}
