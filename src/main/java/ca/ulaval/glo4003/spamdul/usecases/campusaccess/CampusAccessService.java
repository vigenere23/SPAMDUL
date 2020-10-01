package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import java.time.DayOfWeek;

public class CampusAccessService extends AccessGrantedObservable {

  private final UserService userService;
  private final CarService carService;
  private final CampusAccessFactory campusAccessFactory;
  private final CampusAccessRepository campusAccessRepository;
  private final PassRepository passRepository;

  public CampusAccessService(UserService userService,
                             CarService carService,
                             CampusAccessFactory campusAccessFactory,
                             CampusAccessRepository campusAccessRepository,
                             PassRepository passRepository) {
    this.userService = userService;
    this.carService = carService;
    this.campusAccessFactory = campusAccessFactory;
    this.campusAccessRepository = campusAccessRepository;
    this.passRepository = passRepository;
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

  public boolean grantAccessToCampus(AccessingCampusDto accessingCampusDto) {
    CampusAccess campusAccess;

    try {
      campusAccess = campusAccessRepository.findById(accessingCampusDto.campusAccessCode);
    } catch (CampusAccessNotFoundException e) {
      return false;
    }

    boolean accessGranted = campusAccess.isAccessGranted(accessingCampusDto.accessingCampusDate);
    if (accessGranted) {
      Pass pass = passRepository.findByPassCode(campusAccess.getAssociatedPassCode());
      if (pass != null) {
        notifyAccessGrantedWithCampusAccess(pass.getParkingZone(),
                                            accessingCampusDto.accessingCampusDate);
      }
    }

    return accessGranted;
  }

  public void associatePassToCampusAccess(CampusAccessCode campusAccessCode,
                                          PassCode passCode,
                                          PassType passType,
                                          DayOfWeek dayOfWeek) {
    CampusAccess campusAccess = campusAccessRepository.findById(campusAccessCode);
    campusAccessRepository.save(campusAccess);
    campusAccess.associatePass(passCode, passType, dayOfWeek);
  }
}
