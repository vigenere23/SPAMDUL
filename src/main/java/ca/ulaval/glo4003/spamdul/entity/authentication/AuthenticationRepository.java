package ca.ulaval.glo4003.spamdul.entity.authentication;

import ca.ulaval.glo4003.spamdul.usecases.authentification.TemporaryToken;

public interface AuthenticationRepository {

  RegisteredUser findBy(String userName, String hashedPassword);

  RegisteredUser findBy(TemporaryToken temporaryToken);

  void save(TemporaryToken temporaryToken, RegisteredUser registeredUser);
}
