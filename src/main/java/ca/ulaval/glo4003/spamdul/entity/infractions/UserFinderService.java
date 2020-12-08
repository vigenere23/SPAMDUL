package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;

public class UserFinderService {
  private final UserRepository userRepository;

  public UserFinderService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findBy(PassCode passCode) {
    return userRepository.findBy(passCode);
  }
}
