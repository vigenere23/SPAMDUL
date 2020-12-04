package ca.ulaval.glo4003.spamdul.usecases.user;

import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class UserService {

  private final UserRepository userRepository;
  private final UserFactory userFactory;

  public UserService(UserRepository userRepository, UserFactory userFactory) {
    this.userRepository = userRepository;
    this.userFactory = userFactory;
  }

  public UserId createUser(UserDto userDto) {
    User user = userFactory.create(userDto.name, userDto.gender, userDto.birthDate, userDto.carDto);
    userRepository.save(user);

    return user.getUserId();
  }
}
