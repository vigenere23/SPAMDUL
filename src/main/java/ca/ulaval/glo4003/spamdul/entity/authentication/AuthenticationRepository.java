package ca.ulaval.glo4003.spamdul.entity.authentication;

public interface AuthenticationRepository {

  RegisteredUser findBy(String userName, String hashedPassword);

  RegisteredUser findBy(TemporaryToken temporaryToken);

  void save(TemporaryToken temporaryToken, RegisteredUser registeredUser);
}
