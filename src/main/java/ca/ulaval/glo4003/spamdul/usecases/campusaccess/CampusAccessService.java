package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
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
  private final BankRepository bankRepository;
  private final TransactionFactory transactionFactory;
  private final CampusAccessFeeRepository campusAccessFeeRepository;

  public CampusAccessService(UserService userService,
                             CarService carService,
                             CampusAccessFactory campusAccessFactory,
                             CampusAccessRepository campusAccessRepository,
                             PassRepository passRepository,
                             Calendar calendar,
                             BankRepository bankRepository,
                             CampusAccessFeeRepository campusAccessFeeRepository,
                             TransactionFactory transactionFactory) {
    this.userService = userService;
    this.carService = carService;
    this.campusAccessFactory = campusAccessFactory;
    this.campusAccessRepository = campusAccessRepository;
    this.passRepository = passRepository;
    this.calendar = calendar;
    this.bankRepository = bankRepository;
    this.transactionFactory = transactionFactory;
    this.campusAccessFeeRepository = campusAccessFeeRepository;
  }

  public CampusAccess createAndSaveNewCampusAccess(CampusAccessDto campusAccessDto) {
    User user = userService.createUser(campusAccessDto.userDto);
    Car car = carService.createCar(campusAccessDto.carDto);

    CampusAccess campusAccess = campusAccessFactory.create(user.getUserId(),
                                                           car.getCarId(),
                                                           campusAccessDto.timePeriodDto);

    userService.saveUser(user);
    carService.saveCar(car);
    campusAccessRepository.save(campusAccess);

    TransactionDto transactionDto = new TransactionDto();
    CarType carType = campusAccessDto.carDto.carType;
    transactionDto.transactionType = TransactionType.CAMPUS_ACCESS;
    transactionDto.carType = carType;
    transactionDto.amount = campusAccessFeeRepository.findBy(carType, campusAccessDto.timePeriodDto.periodType)
                                                     .getFee();
    Transaction transaction = transactionFactory.create(transactionDto);
    MainBankAccount mainBankAccount = bankRepository.getMainBankAccount();
    mainBankAccount.addTransaction(transaction);
    bankRepository.save(mainBankAccount);

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
      notifyAccessGrantedWithCampusAccess(campusAccess.getParkingZone(passRepository), now.toLocalDate());
    }

    return accessGranted;
  }

  public void associatePassToCampusAccess(CampusAccessCode campusAccessCode,
                                          PassCode passCode,
                                          TimePeriod passTimePeriod) {
    CampusAccess campusAccess = campusAccessRepository.findById(campusAccessCode);
    campusAccess.associatePass(passCode, passTimePeriod);
    campusAccessRepository.save(campusAccess);
  }
}
