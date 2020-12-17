package ca.ulaval.glo4003.spamdul.assemblers.authentification;

import ca.ulaval.glo4003.spamdul.authentication.entities.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import javax.ws.rs.core.Cookie;

public class AccessTokenCookieAssembler {

  public TemporaryToken from(Cookie accessToken) {
    if (accessToken == null) {
      throw new NoRegisteredUserLoggedInException();
    }

    return TemporaryToken.valueOf(accessToken.getValue());
  }
}
