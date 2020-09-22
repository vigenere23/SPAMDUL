package ca.ulaval.glo4003.projet.base.ws.domain.user;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;

public class UserService {

  private final UserAssembler userAssembler;
  private final UserRepository userRepository;

  public UserService(UserAssembler userAssembler,
                     UserRepository userRepository) {
    this.userAssembler = userAssembler;
    this.userRepository = userRepository;
  }

  public User addUser(UserDto userDto) {
    User user = userAssembler.create(userDto);

    return userRepository.save(user);
  }
}
