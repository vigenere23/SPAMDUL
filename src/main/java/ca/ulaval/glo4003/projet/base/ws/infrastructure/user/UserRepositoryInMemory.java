package ca.ulaval.glo4003.projet.base.ws.infrastructure.user;

import ca.ulaval.glo4003.projet.base.ws.domain.user.User;
import ca.ulaval.glo4003.projet.base.ws.domain.user.UserId;
import ca.ulaval.glo4003.projet.base.ws.domain.user.UserRepository;
import java.util.HashMap;
import java.util.Map;

public class UserRepositoryInMemory implements UserRepository {

  private Map<UserId, User> registeredUsers;

  public UserRepositoryInMemory() {
    registeredUsers = new HashMap<>();
  }

  public User save(User user) {
    System.out.println("Saved user: " + user.getId());

    registeredUsers.put(user.getId(), user);

    return user;
  }

  public User findById(UserId userId) {
    return registeredUsers.get(userId);
  }
}
