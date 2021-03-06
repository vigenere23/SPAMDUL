package ca.ulaval.glo4003.spamdul.authentication.usecases;

import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.RegisteredUser;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;

public class AuthenticationUseCase {

  private final AuthenticationRepository repository;

  public AuthenticationUseCase(AuthenticationRepository repository) {
    this.repository = repository;
  }

  public TemporaryToken login(String userName, String hashedPassword) {
    RegisteredUser registeredUser = repository.findBy(userName, hashedPassword);
    TemporaryToken temporaryToken = registeredUser.generateTemporaryToken();

    repository.save(temporaryToken, registeredUser);

    return temporaryToken;
  }

  public RegisteredUser findRegisteredUser(TemporaryToken temporaryToken) {
    return repository.findBy(temporaryToken);
  }
}
