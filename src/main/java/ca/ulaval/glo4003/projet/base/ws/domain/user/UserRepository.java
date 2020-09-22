package ca.ulaval.glo4003.projet.base.ws.domain.user;

public interface UserRepository {

  User save(User user);

  User findById(UserId userId);
}
