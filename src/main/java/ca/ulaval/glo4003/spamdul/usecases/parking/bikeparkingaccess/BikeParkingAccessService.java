package ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess.BikeParkingAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess.BikeParkingAccessValidator;
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

  public boolean canAccessParking(BikeParkingAccessCode bikeParkingAccessCode) {
    try {
      User user = userRepository.findBy(bikeParkingAccessCode);
      return user.isAccessGrantedToBikeParking(bikeParkingAccessValidator);
    } catch (UserNotFoundException e) {
      return false;
    }
  }
}
