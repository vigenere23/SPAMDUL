package ca.ulaval.glo4003.spamdul.context.authentication;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.InMemoryAuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class AuthenticationContext implements ResourceContext {

  private final AuthenticationResource authenticationResource;
  private final AuthenticationRepository authenticationRepository;
  private final AccessTokenCookieAssembler accessTokenCookieAssembler;

  public AuthenticationContext() {
    authenticationRepository = new InMemoryAuthenticationRepository();
    AuthenticationService authenticationService = new AuthenticationService(authenticationRepository);
    authenticationResource = new AuthenticationResource(authenticationService);
    accessTokenCookieAssembler = new AccessTokenCookieAssembler();
  }

  public AuthenticationRepository getAuthenticationRepository() {
    return authenticationRepository;
  }

  public AccessTokenCookieAssembler getAccessTokenCookieAssembler() {
    return accessTokenCookieAssembler;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(authenticationResource);
    resources.add(new AuthenticationExceptionAssembler());
    resources.add(new UserExceptionAssembler());
  }
}
