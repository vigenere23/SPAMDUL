package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessServiceTest {

  private static final PassCode A_PASS_CODE = new PassCode();
  private static final UserId A_USER_ID = new UserId();
  private static final CarId A_CAR_ID = new CarId();
  private static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  private static final String A_LICENSE_PLATE_STRING = "xxx xxx";
  private static final Car A_CAR = new Car(A_CAR_ID,
                                           A_CAR_TYPE,
                                           "brand",
                                           "model",
                                           2020,
                                           new LicensePlate(A_LICENSE_PLATE_STRING));
  private static final User A_USER = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1), A_CAR);
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final PeriodType A_PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.ALL);
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private static final int AN_AMOUNT_VALUE = 120;
  private static final Amount AN_AMOUNT = Amount.valueOf(AN_AMOUNT_VALUE);
  private static final Amount A_CAMPUS_ACCESS_FEE = AN_AMOUNT;
  private static final Transaction A_TRANSACTION = new Transaction(AN_AMOUNT,
                                                                   LocalDateTime.now(),
                                                                   TransactionType.CAMPUS_ACCESS);

  private CampusAccess campusAccess;
  @Mock
  private UserRepository userRepository;
  @Mock
  private CampusAccessFactory campusAccessFactory;
  @Mock
  private UserFactory userFactory;
  private CampusAccessDto campusAccessDto;
  private UserDto userDto;
  private AccessingCampusDto accessingCampusDto;
  @Mock
  private Calendar calendar;
  @Mock
  private CampusAccessFeeRepository campusAccessFeeRepository;
  @Mock
  private CampusAccessTransactionService campusAccessTransactionService;

  private CampusAccessService campusAccessService;

  @Before
  public void setUp() throws Exception {
    userDto = new UserDto();
    campusAccessDto = new CampusAccessDto();
    campusAccessDto.timePeriodDto = A_TIME_PERIOD_DTO;
    campusAccessDto.userId = A_USER_ID;
    campusAccessService = new CampusAccessService(campusAccessFactory,
                                                  userRepository,
                                                  calendar,
                                                  campusAccessFeeRepository,
                                                  campusAccessTransactionService);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_PERIOD_TYPE, A_TIME_PERIOD);

    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;

    when(campusAccessFeeRepository.findBy(any(CarType.class), any(PeriodType.class))).thenReturn(A_CAMPUS_ACCESS_FEE);
    when(userRepository.findBy(A_USER_ID)).thenReturn(A_USER);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);
  }

  @Test
  public void whenCreatingNewUser_shouldCallUserFactory() {
    userFactory.create(userDto);

    verify(userFactory, times(1)).create(userDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallCampusAccessFactoryToCreateNewCampusAccess() {
    campusAccessService.createNewCampusAccess(campusAccessDto);
    when(userRepository.findBy(A_USER_ID)).thenReturn(A_USER);

    verify(campusAccessFactory, times(1)).create(A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingNewCampusAccess_associateCampusAccessToUser() {
    when(campusAccessFactory.create(A_TIME_PERIOD_DTO)).thenReturn(campusAccess);
    when(userRepository.findBy(A_USER_ID)).thenReturn(A_USER);

    campusAccessService.createNewCampusAccess(campusAccessDto);

    assertThat(A_USER.doesOwn(A_CAMPUS_ACCESS_CODE)).isTrue();
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldAddRevenueToCampusAccessBankAccount() {
    when(campusAccessFeeRepository.findBy(A_CAR_TYPE, campusAccessDto.timePeriodDto.periodType)).thenReturn(
        A_CAMPUS_ACCESS_FEE);
    when(userRepository.findBy(A_USER_ID)).thenReturn(A_USER);

    campusAccessService.createNewCampusAccess(campusAccessDto);

    verify(campusAccessTransactionService, times(1)).addRevenue(A_TRANSACTION.getAmount(), A_CAR_TYPE);
  }

  @Test
  public void givenACampusAccessCode_whenVerifyingIfCanAccessCampus_shouldFindTheRightUserFromCode() {
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(A_USER);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(userRepository, times(1)).findBy(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void givenALicensePlate_whenVerifyingIfCanAccessCampus_shouldFindTheRightUserFromLicensePlate() {
    LicensePlate licensePlate = new LicensePlate(A_LICENSE_PLATE_STRING);
    accessingCampusDto.campusAccessCode = null;
    accessingCampusDto.licensePlate = licensePlate;
    when(userRepository.findBy(licensePlate)).thenReturn(A_USER);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(userRepository, times(1)).findBy(licensePlate);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldCallCalendarNow() {
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(A_USER);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(calendar, times(1)).now();
  }

  @Test
  public void givenGrantedAccess_whenVerifyingIfCanAccessCampus_shouldReturnTrue() {
    A_USER.associate(campusAccess);
    A_USER.associate(A_PASS);
    when(userRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(A_USER);
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


}
