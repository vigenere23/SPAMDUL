package ca.ulaval.glo4003.spamdul.entity.user;

public interface UserRepository {

  User save(User user);

  User findById(UserId userId);

  void clear();
}
