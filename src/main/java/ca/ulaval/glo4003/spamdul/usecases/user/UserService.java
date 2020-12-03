package ca.ulaval.glo4003.spamdul.usecases.user;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;

public class UserService {

  private UserRepository userRepository;
  private UserFactory userFactory;

  public UserService(UserRepository userRepository, UserFactory userFactory) {
    this.userRepository = userRepository;
    this.userFactory = userFactory;
  }

  public UserId createNewUser(UserDto userDto) {
    User user = userFactory.create(userDto);
    userRepository.save(user);

    return user.getUserId();
  }

}
