package ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;

public class BikeParkingAccessService {

  private final UserRepository userRepository;
  private final BikeParkingAccessValidator bikeParkingAccessValidator;

  public BikeParkingAccessService(UserRepository userRepository,
                                  BikeParkingAccessValidator bikeParkingAccessValidator) {
    this.userRepository = userRepository;
    this.bikeParkingAccessValidator = bikeParkingAccessValidator;
  }

  public boolean canAccessParking(BikeParkingPassCode bikeParkingPassCode) {
    try {
      User user = userRepository.findBy(bikeParkingPassCode);
      return user.isAccessGrantedToBikeParking(bikeParkingAccessValidator);
    } catch (UserNotFoundException e) {
      return false;
    }
  }
}
