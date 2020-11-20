package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AuthenticationResourceImpl implements AuthenticationResource {

  private final AuthenticationService authenticationService;

  public AuthenticationResourceImpl(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override public Response login(LoginRequest loginRequest) {
    TemporaryToken temporaryToken = authenticationService.login(loginRequest.username, loginRequest.hashedPassword);

    // retourner un objet json au lieu de mettre le token dans le cookie
    return Response.status(Status.OK)
                   .cookie(new NewCookie("accessToken",
                                         temporaryToken.toString(),
                                         "/",
                                         "",
                                         "",
                                         100,
                                         false))
                   .build();
  }
}
