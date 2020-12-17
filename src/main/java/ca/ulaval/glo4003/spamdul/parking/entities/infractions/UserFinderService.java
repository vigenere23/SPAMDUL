package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;

public class UserFinderService {
  private final UserRepository userRepository;

  public UserFinderService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findBy(CarParkingPassCode passCode) {
    return userRepository.findBy(passCode);
  }
}
