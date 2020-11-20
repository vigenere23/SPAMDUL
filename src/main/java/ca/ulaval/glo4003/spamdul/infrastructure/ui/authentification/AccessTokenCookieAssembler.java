package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import javax.ws.rs.core.Cookie;

public class AccessTokenCookieAssembler {

  public TemporaryToken from(Cookie accessToken) {
    if (accessToken == null) {
      throw new NoRegisteredUserLoggedInException();
    }

    return TemporaryToken.valueOf(accessToken.getValue());
  }

}
