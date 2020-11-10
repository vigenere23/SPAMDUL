package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
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
  private BankRepository bankRepository;
  private TransactionFactory transactionFactory;
  private CampusAccessFeeRepository campusAccessFeeRepository;

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

    CampusAccess campusAccess = campusAccessFactory.create(user,
                                                           car,
                                                           campusAccessDto.timePeriodDto);

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
      if (accessingCampusDto.campusAccessCode != null) {
        campusAccess = campusAccessRepository.findBy(accessingCampusDto.campusAccessCode);
      } else {
        campusAccess = campusAccessRepository.findBy(accessingCampusDto.licensePlate);
      }
    } catch (CampusAccessNotFoundException e) {
      return false;
    }

    LocalDateTime now = calendar.now();
    boolean accessGranted = campusAccess.isAccessGranted(now);
    if (accessGranted) {
      notifyAccessGranted(campusAccess, now);
    }

    return accessGranted;
  }

  private void notifyAccessGranted(CampusAccess campusAccess, LocalDateTime now) {
    PassCode passCode = campusAccess.getAssociatedPassCode();

    if (passCode != null) {
      Pass pass = passRepository.findByPassCode(campusAccess.getAssociatedPassCode());
      notifyAccessGrantedWithCampusAccess(pass.getParkingZone(), now.toLocalDate());
    }
    // TODO: mais le passCode peut aussi être nul dans d'autres aventure, à implémenter (NullObject pattern)
  }

  public void associatePassToCampusAccess(CampusAccessCode campusAccessCode,
                                          PassCode passCode,
                                          TimePeriod passTimePeriod) {
    CampusAccess campusAccess = campusAccessRepository.findBy(campusAccessCode);
    campusAccess.associatePass(passCode, passTimePeriod);
    campusAccessRepository.save(campusAccess);
  }
}
