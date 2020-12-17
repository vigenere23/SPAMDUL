package ca.ulaval.glo4003.spamdul.context.authentication;

import ca.ulaval.glo4003.spamdul.api.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.InMemoryAuthenticationRepository;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationUseCase;

public class AuthenticationContext implements ResourceContext {

  private final AuthenticationResource authenticationResource;
  private final AuthenticationRepository authenticationRepository;
  private final AccessTokenCookieAssembler accessTokenCookieAssembler;

  public AuthenticationContext() {
    authenticationRepository = new InMemoryAuthenticationRepository();
    AuthenticationUseCase authenticationUseCase = new AuthenticationUseCase(authenticationRepository);
    authenticationResource = new AuthenticationResource(authenticationUseCase);
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
  }
}
