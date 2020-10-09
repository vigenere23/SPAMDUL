package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
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
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;

import java.time.LocalDateTime;

public class CampusAccessService extends AccessGrantedObservable {

  private final UserService userService;
  private final CarService carService;
  private final CampusAccessFactory campusAccessFactory;
  private final CampusAccessRepository campusAccessRepository;
  private final PassRepository passRepository;
  private final Calendar calendar;

  public CampusAccessService(UserService userService,
                             CarService carService,
                             CampusAccessFactory campusAccessFactory,
                             CampusAccessRepository campusAccessRepository,
                             PassRepository passRepository,
                             Calendar calendar) {
    this.userService = userService;
    this.carService = carService;
    this.campusAccessFactory = campusAccessFactory;
    this.campusAccessRepository = campusAccessRepository;
    this.passRepository = passRepository;
    this.calendar = calendar;
  }

  public CampusAccess createAndSaveNewCampusAccess(CampusAccessDto campusAccessDto) {
    User user = userService.createUser(campusAccessDto.userDto);
    Car car = carService.createCar(campusAccessDto.carDto);

    CampusAccess campusAccess = campusAccessFactory.create(user.getUserId(),
                                                           car.getCarId(),
                                                           campusAccessDto.timePeriodDto);

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

    LocalDateTime now = calendar.now();
    boolean accessGranted = campusAccess.isAccessGranted(now);
    if (accessGranted) {
      PassCode passCode = campusAccess.getAssociatedPassCode();
      if (passCode != null) {
        // TODO: maybe change observer contract to use dateTimes instead for more flexibility
        Pass pass = passRepository.findByPassCode(campusAccess.getAssociatedPassCode());
        notifyAccessGrantedWithCampusAccess(pass.getParkingZone(), now.toLocalDate());
      }
      // TODO: mais le passCode peut aussi être nul dans d'autres aventure, à implémenter (NullObject pattern)
    }

    return accessGranted;
  }

  public void associatePassToCampusAccess(CampusAccessCode campusAccessCode,
                                          PassCode passCode,
                                          TimePeriod passTimePeriod) {
    CampusAccess campusAccess = campusAccessRepository.findById(campusAccessCode);
    campusAccessRepository.save(campusAccess);
    campusAccess.associatePass(passCode, passTimePeriod);
  }
}
