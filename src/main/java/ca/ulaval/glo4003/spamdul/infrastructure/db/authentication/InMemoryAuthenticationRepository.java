package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.entity.authentication.RegisteredUser;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception.WrongCredentialsException;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import java.util.HashMap;

public class InMemoryAuthenticationRepository implements AuthenticationRepository {

  private HashMap<String, HashMap<String, RegisteredUser>> registeredUsers;
  private static HashMap<TemporaryToken, RegisteredUser> loggedInRegisteredUsers = new HashMap<>();

  public InMemoryAuthenticationRepository() {
    HashMap<String, RegisteredUser> karine = new HashMap<>();
    karine.put("hashed_password", new RegisteredUser("Karine", AccessLevel.ADMIN));

    HashMap<String, RegisteredUser> dominique = new HashMap<>();
    dominique.put("hashed_password", new RegisteredUser("Dominique", AccessLevel.SSP_AGENT));

    registeredUsers = new HashMap<>();
    registeredUsers.put("Karine", karine);
    registeredUsers.put("Dominique", dominique);
  }

  public RegisteredUser findBy(String username, String hashedPassword) {
    HashMap<String, RegisteredUser> usernameInfos = registeredUsers.get(username);

    if (usernameInfos == null) {
      throw new WrongCredentialsException();
    }

    RegisteredUser registeredUser = usernameInfos.get(hashedPassword);

    if (registeredUser == null) {
      throw new WrongCredentialsException();
    }

    return registeredUser;
  }

  public void save(TemporaryToken temporaryToken, RegisteredUser registeredUser) {
    loggedInRegisteredUsers.put(temporaryToken, registeredUser);
  }

  public RegisteredUser findBy(TemporaryToken temporaryToken) {
    RegisteredUser registeredUser = loggedInRegisteredUsers.get(temporaryToken);

    if (registeredUser == null) {
      throw new NoRegisteredUserLoggedInException();
    }

    return registeredUser;
  }
}
