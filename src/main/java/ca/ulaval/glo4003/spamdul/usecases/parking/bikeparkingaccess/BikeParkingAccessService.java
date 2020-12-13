package ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;

public class BikeParkingAccessService extends AccessGrantedObservable {

  private final UserRepository userRepository;
  private final BikeParkingAccessValidator bikeParkingAccessValidator;
  private final Calendar calendar;

  public BikeParkingAccessService(UserRepository userRepository,
                                  BikeParkingAccessValidator bikeParkingAccessValidator,
                                  Calendar calendar) {
    this.userRepository = userRepository;
    this.bikeParkingAccessValidator = bikeParkingAccessValidator;
    this.calendar = calendar;
  }

  public boolean canAccessParking(BikeParkingPassCode bikeParkingPassCode) {
    try {
      User user = userRepository.findBy(bikeParkingPassCode);
      boolean accessGranted = user.isAccessGrantedToBikeParking(bikeParkingAccessValidator);
      if (accessGranted) {
        // TODO: add test
        notifyAccessGranted(ParkingZone.ZONE_BIKE, calendar.now().toLocalDate());
      }
      return accessGranted;
    } catch (UserNotFoundException e) {
      return false;
    }
  }
}
