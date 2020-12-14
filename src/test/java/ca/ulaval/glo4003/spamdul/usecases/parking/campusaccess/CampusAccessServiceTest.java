package ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.exceptions.UserMustOwnACarToPurchaseACarParkingPassException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessServiceTest {

  private static final ParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("123");
  private static final UserId A_USER_ID = UserId.valueOf("123");
  private static final CarId A_CAR_ID = new CarId();
  private static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  private static final String A_LICENSE_PLATE_STRING = "xxx xxx";
  private static final Car A_CAR = new Car(A_CAR_ID,
                                           A_CAR_TYPE,
                                           "brand",
                                           "model",
                                           2020,
                                           new LicensePlate(A_LICENSE_PLATE_STRING));
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("123");
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final PeriodType A_PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.ALL);
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final CarParkingPass A_PARKING_PASS = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private static final int AN_AMOUNT_VALUE = 120;
  private static final Amount AN_AMOUNT = Amount.valueOf(AN_AMOUNT_VALUE);
  private static final Amount A_CAMPUS_ACCESS_FEE = AN_AMOUNT;
  private static final Transaction A_TRANSACTION = new Transaction(AN_AMOUNT,
                                                                   LocalDateTime.now(),
                                                                   TransactionType.CAMPUS_ACCESS);

  private CampusAccessService campusAccessService;
  private AccessingCampusDto accessingCampusDto;
  private CampusAccessDto campusAccessDto;
  private CampusAccess campusAccess;
  private User user;
  @Mock
  private UserRepository userRepository;
  @Mock
  private CampusAccessFactory campusAccessFactory;
  @Mock
  private Calendar calendar;
  @Mock
  private CampusAccessFeeRepository campusAccessFeeRepository;
  @Mock
  private CampusAccessTransactionService campusAccessTransactionService;
  @Mock
  private CampusAccessDtoAssembler campusAccessDtoAssembler;


  @Before
  public void setUp() throws Exception {
    campusAccessDto = new CampusAccessDto();
    campusAccessDto.timePeriodDto = A_TIME_PERIOD_DTO;
    campusAccessDto.userId = A_USER_ID;
    campusAccessService = new CampusAccessService(campusAccessFactory,
                                                  userRepository,
                                                  calendar,
                                                  campusAccessFeeRepository,
                                                  campusAccessTransactionService,
                                                  campusAccessDtoAssembler);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_TIME_PERIOD);

    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;

    user = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1), A_CAR);

    when(campusAccessFeeRepository.findBy(any(CarType.class), any(PeriodType.class))).thenReturn(A_CAMPUS_ACCESS_FEE);
    when(userRepository.findBy(A_USER_ID)).thenReturn(user);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallCampusAccessFactoryToCreateNewCampusAccess() {
    when(userRepository.findBy(A_USER_ID)).thenReturn(user);

    campusAccessService.createCampusAccess(campusAccessDto);

    verify(campusAccessFactory, times(1)).create(A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingNewCampusAccess_associateCampusAccessToUser() {
    when(campusAccessFactory.create(A_TIME_PERIOD_DTO)).thenReturn(campusAccess);
    when(userRepository.findBy(A_USER_ID)).thenReturn(user);

    campusAccessService.createCampusAccess(campusAccessDto);

    assertThat(user.doesOwn(A_CAMPUS_ACCESS_CODE)).isTrue();
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldAddRevenueToCampusAccessBankAccount() {
    when(campusAccessFeeRepository.findBy(A_CAR_TYPE, campusAccessDto.timePeriodDto.periodType)).thenReturn(
        A_CAMPUS_ACCESS_FEE);
    when(userRepository.findBy(A_USER_ID)).thenReturn(user);

    campusAccessService.createCampusAccess(campusAccessDto);

    verify(campusAccessTransactionService, times(1)).addRevenue(A_TRANSACTION.getAmount(), A_CAR_TYPE);
  }

  @Test
  public void givenACampusAccessCode_whenVerifyingIfCanAccessCampus_shouldFindTheRightUserFromCode() {
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(user);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(userRepository, times(1)).findBy(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void givenALicensePlate_whenVerifyingIfCanAccessCampus_shouldFindTheRightUserFromLicensePlate() {
    LicensePlate licensePlate = new LicensePlate(A_LICENSE_PLATE_STRING);
    accessingCampusDto.campusAccessCode = null;
    accessingCampusDto.licensePlate = licensePlate;
    when(userRepository.findBy(licensePlate)).thenReturn(user);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(userRepository, times(1)).findBy(licensePlate);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldCallCalendarNow() {
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(user);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(calendar, times(1)).now();
  }

  @Test
  public void givenGrantedAccess_whenVerifyingIfCanAccessCampus_shouldReturnTrue() {
    user.associate(campusAccess);
    user.associateCarParkingPass(A_PARKING_PASS);
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(user);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);

    boolean result = campusAccessService.grantAccessToCampus(accessingCampusDto);

    assertThat(result).isTrue();
  }

  @Test
  public void givenAnUnregisteredCampusAccessCode_whenVerifyingIfCanAccessCampus_shouldNotGrantAccess() {
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenThrow(UserNotFoundException.class);

    boolean isGrantedAccess = campusAccessService.grantAccessToCampus(accessingCampusDto);

    assertThat(isGrantedAccess).isFalse();
  }

  @Test(expected = UserMustOwnACarToPurchaseACarParkingPassException.class)
  public void givenAUserThatDoesNotOwnACar_whenBuyingACarParkingPass_shouldThrowException() {
    user = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1));
    when(userRepository.findBy(A_USER_ID)).thenReturn(user);

    campusAccessService.createCampusAccess(campusAccessDto);
  }
}
