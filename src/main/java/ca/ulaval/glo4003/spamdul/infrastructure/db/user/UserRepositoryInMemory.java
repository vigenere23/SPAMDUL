package ca.ulaval.glo4003.spamdul.infrastructure.db.user;

import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UserRepositoryInMemory implements UserRepository {

  private static final Map<UserId, User> registeredUsers = new HashMap<>();
  private final Logger logger = Logger.getLogger(UserRepository.class.getName());

  public UserId save(User user) {
    registeredUsers.put(user.getUserId(), user);

    String loggingInfos = String.format("Saving user: %s", user.getUserId().toString());
    logger.info(loggingInfos);

    return user.getUserId();
  }

  public User findById(UserId userId) {
    User user = registeredUsers.get(userId);

    if (user == null) {
      throw new UserNotFoundException("User id does not correspond to a valid user");
    }

    return user;
  }
}
