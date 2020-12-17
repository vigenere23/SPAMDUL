package ca.ulaval.glo4003.spamdul.api.carboncredits;

import ca.ulaval.glo4003.spamdul.api.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsUseCase;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/carbon-credits")
public class CarbonCreditsResource {

  private final CarbonCreditsUseCase carbonCreditsUseCase;
  private final AccessTokenCookieAssembler cookieAssembler;

  public CarbonCreditsResource(CarbonCreditsUseCase carbonCreditsUseCase,
                               AccessTokenCookieAssembler cookieAssembler) {
    this.carbonCreditsUseCase = carbonCreditsUseCase;
    this.cookieAssembler = cookieAssembler;
  }

  @PUT
  @Path("/activate")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response activateAutomaticTransfer(CarbonCreditsToggleDto request,
                                            @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken token = cookieAssembler.from(accessToken);
    CarbonCreditsToggleDto response = new CarbonCreditsToggleDto();
    response.active = carbonCreditsUseCase.activateAutomaticTransfer(request.active, token);

    return Response.status(Response.Status.OK)
                   .entity(response)
                   .build();
  }
}
