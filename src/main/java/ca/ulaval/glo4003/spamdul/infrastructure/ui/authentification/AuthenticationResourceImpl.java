package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.AuthenticationResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AuthenticationResourceImpl implements AuthenticationResource {

  private AuthenticationService authenticationService;

  public AuthenticationResourceImpl(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  public Response login(LoginRequest loginRequest) {
    TemporaryToken temporaryToken = authenticationService.login(loginRequest.userName, loginRequest.hashedPassword);

    return Response.status(Status.OK)
        .cookie(new NewCookie("accessToken", temporaryToken.toString()))
        .build();
  }
}
