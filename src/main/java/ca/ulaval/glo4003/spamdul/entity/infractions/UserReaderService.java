package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;

public class UserReaderService {
  private final UserRepository userRepository;

  public UserReaderService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User readUserBy(PassCode passCode) {
    return userRepository.findBy(passCode);
  }
}
