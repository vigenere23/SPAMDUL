package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDateTime;

public class CampusAccessService extends AccessGrantedObservable {

  private final UserFactory userFactory;
  private final CampusAccessFactory campusAccessFactory;
  private final UserRepository userRepository;
  private final Calendar calendar;
  private final CampusAccessFeeRepository campusAccessFeeRepository;
  private final CampusAccessTransactionService campusAccessTransactionService;

  //TODO trouver un nouveau nom qui exprime que ca permet de gerer les user les campus access et les passe
  public CampusAccessService(UserFactory userFactory,
                             CampusAccessFactory campusAccessFactory,
                             UserRepository userRepository,
                             Calendar calendar,
                             CampusAccessFeeRepository campusAccessFeeRepository,
                             CampusAccessTransactionService campusAccessTransactionService) {
    this.userFactory = userFactory;
    this.campusAccessFactory = campusAccessFactory;
    this.userRepository = userRepository;
    this.calendar = calendar;
    this.campusAccessFeeRepository = campusAccessFeeRepository;
    this.campusAccessTransactionService = campusAccessTransactionService;
  }

  public UserId createNewUser(UserDto userDto) {
    User user = userFactory.create(userDto);
    userRepository.save(user);

    return user.getUserId();
  }

  public CampusAccess createNewCampusAccess(CampusAccessDto campusAccessDto) {
    CampusAccess campusAccess = campusAccessFactory.create(campusAccessDto.timePeriodDto);

    User user = userRepository.findBy(campusAccessDto.userId);
    user.associate(campusAccess);
    userRepository.save(user);

    //TODO revoir pour ne plus violer le TDA
    addRevenue(user.getCar().getCarType(), campusAccessDto.timePeriodDto.periodType);

    return campusAccess;
  }

  private void addRevenue(CarType carType, PeriodType periodType) {
    Amount amount = campusAccessFeeRepository.findBy(carType, periodType);

    campusAccessTransactionService.addRevenue(amount, carType);
  }

  public void associatePassToUser(UserId userId, Pass pass) {
    User user = userRepository.findBy(userId);
    user.associate(pass);
    userRepository.save(user);
  }

  public boolean grantAccessToCampus(AccessingCampusDto accessingCampusDto) {
    LocalDateTime now = calendar.now();

    try {
      if (accessingCampusDto.campusAccessCode != null) {
        return grantAccess(userRepository.findBy(accessingCampusDto.campusAccessCode), now);
      } else {
        return grantAccess(userRepository.findBy(accessingCampusDto.licensePlate), now);
      }
    } catch (UserNotFoundException e) {
      return false;
    }
  }

  private boolean grantAccess(User user, LocalDateTime dateTime) {
    if (user.isAccessGrantedToCampus(dateTime)) {
      notifyAccessGranted(user, dateTime);
      return true;
    }

    return false;
  }

  private void notifyAccessGranted(User user, LocalDateTime now) {
    notifyAccessGrantedWithCampusAccess(user.getParkingZone(), now.toLocalDate());
  }
}
