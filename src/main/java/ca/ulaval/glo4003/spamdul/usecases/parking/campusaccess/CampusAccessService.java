package ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.AccessGrantedObservable;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.exceptions.UserMustOwnACarToPurchaseACarParkingPassException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import java.time.LocalDateTime;

public class CampusAccessService extends AccessGrantedObservable {

  private final CampusAccessFactory campusAccessFactory;
  private final UserRepository userRepository;
  private final Calendar calendar;
  private final CampusAccessFeeRepository campusAccessFeeRepository;
  private final CampusAccessTransactionService campusAccessTransactionService;
  private CampusAccessDtoAssembler campusAccessDtoAssembler;

  public CampusAccessService(CampusAccessFactory campusAccessFactory,
                             UserRepository userRepository,
                             Calendar calendar,
                             CampusAccessFeeRepository campusAccessFeeRepository,
                             CampusAccessTransactionService campusAccessTransactionService,
                             CampusAccessDtoAssembler campusAccessDtoAssembler) {
    this.campusAccessFactory = campusAccessFactory;
    this.userRepository = userRepository;
    this.calendar = calendar;
    this.campusAccessFeeRepository = campusAccessFeeRepository;
    this.campusAccessTransactionService = campusAccessTransactionService;
    this.campusAccessDtoAssembler = campusAccessDtoAssembler;
  }

  public CampusAccessDto createCampusAccess(CampusAccessDto campusAccessDto) {
    User user = userRepository.findBy(campusAccessDto.userId);

    if (user.getCar() == null) {
      throw new UserMustOwnACarToPurchaseACarParkingPassException();
    }

    CampusAccess campusAccess = campusAccessFactory.create(campusAccessDto.timePeriodDto);

    user.associate(campusAccess);
    userRepository.save(user);


    addRevenue(user.getCar().getCarType(), campusAccessDto.timePeriodDto.periodType);

    return campusAccessDtoAssembler.toDto(campusAccess);
  }

  private void addRevenue(CarType carType, PeriodType periodType) {
    Amount amount = campusAccessFeeRepository.findBy(carType, periodType);

    campusAccessTransactionService.addRevenue(amount, carType);
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
    notifyAccessGranted(user.getParkingZone(), now.toLocalDate());
  }
}
