package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativesResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

//TODO ce nest pas tester
public class FundraisingResourceImp implements FundraisingResource {

  private final InitiativeAssembler initiativeAssembler;
  private final InitiativeService initiativeService;
  private final AccessTokenCookieAssembler cookieAssembler;

  public FundraisingResourceImp(InitiativeAssembler initiativeAssembler,
                                InitiativeService initiativeService,
                                AccessTokenCookieAssembler cookieAssembler) {
    this.initiativeAssembler = initiativeAssembler;
    this.initiativeService = initiativeService;
    this.cookieAssembler = cookieAssembler;
  }

  @Override
  public InitiativesResponse getInitiatives(Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    return initiativeAssembler.toResponse(initiativeService.getAllInitiatives(temporaryToken));
  }

  @Override
  public Response createInitiative(InitiativeRequest request, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    InitiativeResponse responseObject = initiativeAssembler.toResponse(initiativeService.addInitiative(
        initiativeAssembler.fromRequest(request),
        temporaryToken));

    return Response.status(Status.CREATED).entity(responseObject).build();
  }
}
