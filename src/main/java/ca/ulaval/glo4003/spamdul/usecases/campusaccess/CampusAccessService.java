package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;

public class CampusAccessService extends AccessGrantedObservable {

  private final UserService userService;
  private final CarService carService;
  private final CampusAccessFactory campusAccessFactory;
  private final CampusAccessRepository campusAccessRepository;

  public CampusAccessService(UserService userService,
                             CarService carService,
                             CampusAccessFactory campusAccessFactory,
                             CampusAccessRepository campusAccessRepository) {
    this.userService = userService;
    this.carService = carService;
    this.campusAccessFactory = campusAccessFactory;
    this.campusAccessRepository = campusAccessRepository;
  }

  public CampusAccess createAndSaveNewCampusAccess(CampusAccessDto campusAccessDto) {
    User user = userService.createUser(campusAccessDto.userDto);
    Car car = carService.createCar(campusAccessDto.carDto);

    CampusAccess campusAccess = campusAccessFactory.create(user.getUserId(),
                                                           car.getCarId(),
                                                           campusAccessDto.period,
                                                           campusAccessDto.dayToAccessCampus);

    //TODO saleService.buyingCampusAccess(campusAccessDto.period, campusAccessDto.carDto.type);

    userService.saveUser(user);
    carService.saveCar(car);
    campusAccessRepository.save(campusAccess);

    return campusAccess;
  }

  public boolean canAccessCampus(AccessingCampusDto accessingCampusDto) {
    CampusAccess campusAccess;

    try {
      campusAccess = campusAccessRepository.findById(accessingCampusDto.campusAccessCode);
    } catch (CampusAccessNotFoundException e) {
      return false;
    }

    boolean accessGranted = campusAccess.isAccessGranted(accessingCampusDto.accessingCampusDate.getDayOfWeek());
    if (accessGranted) {
      notifyAccessGrantedWithCampusAccess(campusAccess, accessingCampusDto.accessingCampusDate);
    }

    return accessGranted;
  }
}
