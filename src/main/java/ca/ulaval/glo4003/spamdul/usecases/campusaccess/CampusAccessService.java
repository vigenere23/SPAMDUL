package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CampusAccessBankAccount;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDateTime;

public class CampusAccessService extends AccessGrantedObservable {

  private final UserService userService;
  private final CarService carService;
  private final CampusAccessFactory campusAccessFactory;
  private final CampusAccessRepository campusAccessRepository;
  private final Calendar calendar;
  private final CampusAccessFeeRepository campusAccessFeeRepository;
  private final CampusAccessBankAccount campusAccessBankAccount;

  public CampusAccessService(UserService userService,
                             CarService carService,
                             CampusAccessFactory campusAccessFactory,
                             CampusAccessRepository campusAccessRepository,
                             Calendar calendar,
                             CampusAccessFeeRepository campusAccessFeeRepository,
                             CampusAccessBankAccount campusAccessBankAccount) {
    this.userService = userService;
    this.carService = carService;
    this.campusAccessFactory = campusAccessFactory;
    this.campusAccessRepository = campusAccessRepository;
    this.calendar = calendar;
    this.campusAccessFeeRepository = campusAccessFeeRepository;
    this.campusAccessBankAccount = campusAccessBankAccount;
  }

  public CampusAccess createAndSaveNewCampusAccess(CampusAccessDto campusAccessDto) {
    User user = userService.createUser(campusAccessDto.userDto);
    Car car = carService.createCar(campusAccessDto.carDto);

    CampusAccess campusAccess = campusAccessFactory.create(user,
                                                           car,
                                                           campusAccessDto.timePeriodDto);

    campusAccessRepository.save(campusAccess);

    addRevenue(campusAccessDto.carDto.carType, campusAccessDto.timePeriodDto.periodType);

    return campusAccess;
  }

  private void addRevenue(CarType carType, PeriodType periodType) {
    Amount amount = Amount.valueOf(campusAccessFeeRepository.findBy(carType, periodType).getFee());
    
    campusAccessBankAccount.addRevenue(amount, carType);
  }

  public void associatePassToCampusAccess(CampusAccessCode campusAccessCode, Pass pass) {
    CampusAccess campusAccess = campusAccessRepository.findBy(campusAccessCode);
    campusAccess.associatePass(pass);
    campusAccessRepository.save(campusAccess);
  }

  public boolean grantAccessToCampus(AccessingCampusDto accessingCampusDto) {
    LocalDateTime now = calendar.now();
    boolean accessGranted;

    try {
      if (accessingCampusDto.campusAccessCode != null) {
        accessGranted = isAccessGrantedByCampusAccessCode(accessingCampusDto.campusAccessCode, now);
      } else {
        accessGranted = isAccessGrantedByLicensePlate(accessingCampusDto.licensePlate, now);
      }
    } catch (CampusAccessNotFoundException e) {
      return false;
    }

    return accessGranted;
  }

  private boolean isAccessGrantedByCampusAccessCode(CampusAccessCode campusAccessCode, LocalDateTime dateTime) {
    CampusAccess campusAccess = campusAccessRepository.findBy(campusAccessCode);
    boolean accessGranted = campusAccess.grantAccess(dateTime);

    if (accessGranted) {
      notifyAccessGranted(campusAccess, dateTime);
    }

    return accessGranted;
  }

  private boolean isAccessGrantedByLicensePlate(LicensePlate licensePlate, LocalDateTime dateTime) {
    for (CampusAccess campusAccess : campusAccessRepository.findBy(licensePlate)) {
      if (campusAccess.grantAccess(dateTime)) {
        notifyAccessGranted(campusAccess, dateTime);
        return true;
      }
    }
    return false;
  }

  private void notifyAccessGranted(CampusAccess campusAccess, LocalDateTime now) {
    notifyAccessGrantedWithCampusAccess(campusAccess.getParkingZone(), now.toLocalDate());
  }
}
