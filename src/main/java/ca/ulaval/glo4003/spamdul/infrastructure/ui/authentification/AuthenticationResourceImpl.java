package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.AuthenticationResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;
import ca.ulaval.glo4003.spamdul.usecases.authentification.TemporaryToken;

public class AuthenticationResourceImpl implements AuthenticationResource {

  private AuthenticationService authenticationService;

  public AuthenticationResourceImpl(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
    TemporaryToken temporaryToken = authenticationService.login(loginRequest.userName, loginRequest.hashedPassword);
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.temporaryToken = temporaryToken.toString();

    return authenticationResponse;
  }
}
