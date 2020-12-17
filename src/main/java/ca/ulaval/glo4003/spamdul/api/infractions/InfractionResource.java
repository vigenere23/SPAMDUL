package ca.ulaval.glo4003.spamdul.api.infractions;

import ca.ulaval.glo4003.spamdul.api.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.api.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.api.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionUseCase;
import ca.ulaval.glo4003.spamdul.usecases.infraction.dto.InfractionDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/infractions")
public class InfractionResource {

  private final InfractionAssembler infractionAssembler;
  private final InfractionUseCase infractionUseCase;
  private final AccessTokenCookieAssembler cookieAssembler;

  public InfractionResource(InfractionAssembler infractionAssembler,
                            InfractionUseCase infractionUseCase,
                            AccessTokenCookieAssembler cookieAssembler) {
    this.infractionAssembler = infractionAssembler;
    this.infractionUseCase = infractionUseCase;
    this.cookieAssembler = cookieAssembler;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response validateParkingPass(InfractionRequest infractionRequest,
                                      @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken token = cookieAssembler.from(accessToken);
    PassToValidateDto passToValidateDto = infractionAssembler.fromRequest(infractionRequest);
    InfractionDto infractionDto = infractionUseCase.giveInfractionIfNotValid(passToValidateDto, token);

    InfractionResponse infractionResponse = infractionAssembler.toResponse(infractionDto);

    if (infractionResponse == null) {
      return Response
          .status(Status.NO_CONTENT)
          .build();

    } else {
      return Response
          .status(Status.OK)
          .entity(infractionResponse)
          .build();
    }
  }

  @POST
  @Path("/pay")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response payInfraction(InfractionPaymentRequest infractionPaymentRequest) {
    InfractionPaymentDto infractionPaymentDto = infractionAssembler.fromRequest(infractionPaymentRequest);
    infractionUseCase.payInfraction(infractionPaymentDto);

    return Response.status(Status.OK)
                   .build();
  }
}
