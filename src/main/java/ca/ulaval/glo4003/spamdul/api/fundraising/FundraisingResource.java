package ca.ulaval.glo4003.spamdul.api.fundraising;

import ca.ulaval.glo4003.spamdul.api.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.api.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.api.fundraising.dto.InitiativesResponse;
import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeUseCase;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/fundraising")
public class FundraisingResource {

  private final InitiativeAssembler initiativeAssembler;
  private final InitiativeUseCase initiativeUseCase;
  private final AccessTokenCookieAssembler cookieAssembler;

  public FundraisingResource(InitiativeAssembler initiativeAssembler,
                             InitiativeUseCase initiativeUseCase,
                             AccessTokenCookieAssembler cookieAssembler) {
    this.initiativeAssembler = initiativeAssembler;
    this.initiativeUseCase = initiativeUseCase;
    this.cookieAssembler = cookieAssembler;
  }

  @GET
  @Path("/initiatives")
  @Produces(MediaType.APPLICATION_JSON)
  public InitiativesResponse getInitiatives(@CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    return initiativeAssembler.toResponse(initiativeUseCase.getAllInitiatives(temporaryToken));
  }

  @POST
  @Path("/initiatives")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createInitiative(InitiativeRequest request, @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    InitiativeResponse responseObject = initiativeAssembler.toResponse(initiativeUseCase.addInitiative(
        initiativeAssembler.fromRequest(request),
        temporaryToken));

    return Response.status(Status.CREATED).entity(responseObject).build();
  }
}
