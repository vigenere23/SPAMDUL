package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.entity.authentication.RegisteredUser;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception.InvalidCredentialsException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAuthenticationRepository implements AuthenticationRepository {

  private final Map<String, Map<String, RegisteredUser>> registeredUsers = new HashMap<>();
  private static final Map<TemporaryToken, RegisteredUser> loggedInRegisteredUsers = new HashMap<>();

  public InMemoryAuthenticationRepository() {
    Map<String, RegisteredUser> karine = new HashMap<>();
    karine.put("hashed_password", new RegisteredUser("Karine", AccessLevel.ADMIN));

    Map<String, RegisteredUser> dominique = new HashMap<>();
    dominique.put("hashed_password", new RegisteredUser("Dominique", AccessLevel.SSP_AGENT));

    registeredUsers.put("Karine", karine);
    registeredUsers.put("Dominique", dominique);
  }

  @Override public RegisteredUser findBy(String username, String hashedPassword) {
    Map<String, RegisteredUser> usernameInfos = registeredUsers.get(username);

    if (usernameInfos == null) {
      throw new InvalidCredentialsException();
    }

    RegisteredUser registeredUser = usernameInfos.get(hashedPassword);

    if (registeredUser == null) {
      throw new InvalidCredentialsException();
    }

    return registeredUser;
  }

  @Override public void save(TemporaryToken temporaryToken, RegisteredUser registeredUser) {
    loggedInRegisteredUsers.put(temporaryToken, registeredUser);
  }

  @Override public RegisteredUser findBy(TemporaryToken temporaryToken) {
    RegisteredUser registeredUser = loggedInRegisteredUsers.get(temporaryToken);

    if (registeredUser == null) {
      throw new NoRegisteredUserLoggedInException();
    }

    return registeredUser;
  }
}
