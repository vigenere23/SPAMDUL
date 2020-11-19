package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

public class CarbonCreditsResourceImpl implements CarbonCreditsResource {

  private final CarbonCreditsService carbonCreditsService;
  private AccessTokenCookieAssembler cookieAssembler;

  public CarbonCreditsResourceImpl(CarbonCreditsService carbonCreditsService,
                                   AccessTokenCookieAssembler cookieAssembler) {
    this.carbonCreditsService = carbonCreditsService;
    this.cookieAssembler = cookieAssembler;
  }

  @Override
  public Response activateAutomaticTransfer(CarbonCreditsToggleDto request, Cookie accessToken) {
    TemporaryToken token = cookieAssembler.from(accessToken);
    CarbonCreditsToggleDto response = new CarbonCreditsToggleDto();
    response.active = carbonCreditsService.activateAutomaticTransfer(request.active, token);

    return Response.status(Response.Status.OK)
                   .entity(response)
                   .build();
  }
}
