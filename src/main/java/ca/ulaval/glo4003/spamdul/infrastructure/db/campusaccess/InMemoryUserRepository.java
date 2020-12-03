package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class InMemoryUserRepository implements UserRepository {

  private static final Map<UserId, User> users = new HashMap<>();
  private static final Logger logger = Logger.getLogger(InMemoryUserRepository.class.getName());

  public void save(User user) {
    users.put(user.getUserId(), user);
    logger.info(String.format("Saving user : ", user.getUserId().toString()));
  }

  public User findBy(UserId userId) {
    User user = users.get(userId);
    if (user == null) {
      throw new UserNotFoundException();
    }

    return user;
  }

  public User findBy(CampusAccessCode campusAccessCode) {
    Optional<User> user = users.values()
                               .stream()
                               .filter(currentUser -> currentUser.doesOwn(campusAccessCode))
                               .findFirst();

    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  public User findBy(LicensePlate licensePlate) {
    Optional<User> user = users.values()
                               .stream()
                               .filter(currentUser -> currentUser.doesOwn(licensePlate))
                               .findFirst();

    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  public User findBy(InfractionId infractionId) {
    Optional<User> user = users.values()
                               .stream()
                               .filter(currentUser -> currentUser.hasInfractionWith(infractionId))
                               .findFirst();

    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  public User findBy(RechargULCardId rechargULCardId) {
    Optional<User> user = users.values()
                               .stream()
                               .filter(currentUser -> currentUser.doesOwn(rechargULCardId))
                               .findFirst();

    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  public User findBy(PassCode passCode) {
    Optional<User> user = users.values()
                               .stream()
                               .filter(currentUser -> currentUser.doesOwn(passCode))
                               .findFirst();
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  public void deleteAll() {
    users.clear();
  }
}
