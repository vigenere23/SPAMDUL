package ca.ulaval.glo4003.spamdul.authentication.context;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.api.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.infrastructure.persistence.registereduser.InMemoryRegisteredUserRepository;
import ca.ulaval.glo4003.spamdul.authentication.usecases.AuthenticationUseCase;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public class AuthenticationContext implements ResourceContext {

  private final AuthenticationResource authenticationResource;
  private final AuthenticationRepository authenticationRepository;
  private final AccessTokenCookieAssembler accessTokenCookieAssembler;

  public AuthenticationContext() {
    authenticationRepository = new InMemoryRegisteredUserRepository();
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
