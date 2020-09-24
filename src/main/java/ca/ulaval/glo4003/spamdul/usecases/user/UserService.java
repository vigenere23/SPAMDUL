package ca.ulaval.glo4003.spamdul.usecases.user;

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
    User createdUser = userFactory.create(userDto.name,
                                          userDto.gender,
                                          userDto.birthDate,
                                          userDto.dayToAccessCampus);

    return userRepository.save(createdUser);
  }
}