package ca.ulaval.glo4003.spamdul.usecases.campusaccess.user;

import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;

public class UserService {

  private final UserFactory userFactory;

  public UserService(UserFactory userFactory) {
    this.userFactory = userFactory;
  }

  public User createUser(UserDto userDto) {
    return userFactory.create(userDto.name,
                              userDto.gender,
                              userDto.birthDate);
  }
}
