package ca.ulaval.glo4003.spamdul.usecases.campusaccess.user;

import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;

public class UserService {

  private final UserRepository userRepository;
  private final UserFactory userFactory;

  public UserService(UserFactory userFactory, UserRepository userRepository) {
    this.userFactory = userFactory;
    this.userRepository = userRepository;
  }

  public User createUser(UserDto userDto) {
    return userFactory.create(userDto.name,
                              userDto.gender,
                              userDto.birthDate);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}
