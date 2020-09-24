package ca.ulaval.glo4003.spamdul.infrastructure.db.user;

import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UserRepositoryInMemory implements UserRepository {

  private final Map<UserId, User> registeredUsers;
  private final Logger logger = Logger.getLogger(UserRepository.class.getName());

  public UserRepositoryInMemory() {
    registeredUsers = new HashMap<>();
  }

  public User save(User user) {
    registeredUsers.put(user.getId(), user);

    String loggingInfos = String.format("Saved user: %s", user.getId().toString());
    logger.info(loggingInfos);

    return user;
  }

  public User findById(UserId userId) {
    return registeredUsers.get(userId);
  }
}
