package ca.ulaval.glo4003.spamdul.context.authentication;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.InMemoryAuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AuthenticationResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;

public class AuthenticationContext {

  private AuthenticationResource authenticationResource;
  private AuthenticationRepository authenticationRepository;

  public AuthenticationContext() {
    authenticationRepository = new InMemoryAuthenticationRepository();
    AuthenticationService authenticationService = new AuthenticationService(authenticationRepository);
    authenticationResource = new AuthenticationResourceImpl(authenticationService);
  }

  public AuthenticationResource getAuthenticationResource() {
    return authenticationResource;
  }

  public AuthenticationRepository getAuthenticationRepository() {
    return authenticationRepository;
  }
}
