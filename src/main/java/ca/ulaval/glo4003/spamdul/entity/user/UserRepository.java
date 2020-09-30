package ca.ulaval.glo4003.spamdul.entity.user;

public interface UserRepository {

  UserId save(User user);

  User findById(UserId userId);

}
