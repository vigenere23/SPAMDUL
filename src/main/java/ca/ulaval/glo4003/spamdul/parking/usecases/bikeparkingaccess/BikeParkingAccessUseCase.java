package ca.ulaval.glo4003.spamdul.parking.usecases.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.bike.BikeParkingAccessValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;

public class BikeParkingAccessUseCase extends AccessGrantedObservable {

  private final UserRepository userRepository;
  private final BikeParkingAccessValidator bikeParkingAccessValidator;
  private final Calendar calendar;

  public BikeParkingAccessUseCase(UserRepository userRepository,
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
        notifyAccessGranted(ParkingZone.ZONE_BIKE, calendar.now().toLocalDate());
      }
      return accessGranted;
    } catch (UserNotFoundException e) {
      return false;
    }
  }
}
