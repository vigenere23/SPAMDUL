package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
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
  private final InfractionService infractionService;
  private final AccessTokenCookieAssembler cookieAssembler;

  public InfractionResource(InfractionAssembler infractionAssembler,
                            InfractionService infractionService,
                            AccessTokenCookieAssembler cookieAssembler) {
    this.infractionAssembler = infractionAssembler;
    this.infractionService = infractionService;
    this.cookieAssembler = cookieAssembler;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response validateParkingPass(InfractionRequest infractionRequest, @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken token = cookieAssembler.from(accessToken);
    PassToValidateDto passToValidateDto = infractionAssembler.fromRequest(infractionRequest);
    Infraction infraction = infractionService.giveInfractionIfNotValid(passToValidateDto, token);

    InfractionResponse infractionResponse = infractionAssembler.toResponse(infraction);

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
    infractionService.payInfraction(infractionPaymentDto);

    return Response.status(Status.OK)
                   .build();
  }
}
