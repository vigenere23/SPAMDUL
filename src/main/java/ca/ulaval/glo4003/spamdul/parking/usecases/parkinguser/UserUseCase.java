package ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;

public class UserUseCase {

  private final UserRepository userRepository;
  private final UserFactory userFactory;

  public UserUseCase(UserRepository userRepository, UserFactory userFactory) {
    this.userRepository = userRepository;
    this.userFactory = userFactory;
  }

  public UserId createUser(UserCreationDto userCreationDto) {
    User user = userFactory.create(userCreationDto.name,
                                   userCreationDto.gender,
                                   userCreationDto.birthDate,
                                   userCreationDto.carDto);
    userRepository.save(user);

    return user.getId();
  }
}
