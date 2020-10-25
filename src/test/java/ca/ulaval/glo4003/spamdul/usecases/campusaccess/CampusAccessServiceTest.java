package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessServiceTest {

  private static final PassCode A_PASS_CODE = new PassCode();
  private static final PassType A_PASS_TYPE = PassType.MONTHLY;
  private static final UserId A_USER_ID = new UserId();
  private static final CarId A_CAR_ID = new CarId();
  private static final User A_USER = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1));
  private static final Car A_CAR = new Car(A_CAR_ID, CarType.GOURMANDE, "brand", "model", 2020, "xxx xxx");
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.ALL);
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();

  private CampusAccess campusAccess;
  private CampusAccessRepository campusAccessRepository;
  private UserService userService;
  private CarService carService;
  private CampusAccessFactory campusAccessFactory;
  private CampusAccessDto campusAccessDto;
  private UserDto userDto;
  private CarDto carDto;
  private AccessingCampusDto accessingCampusDto;
  private PassRepository passRepository;
  private Calendar calendar;
  private TransactionFactory transactionFactory;
  private BankRepository bankRepository;
  private CampusAccessFeeRepository campusAccessFeeRepository;

  private CampusAccessService campusAccessService;

  @Before
  public void setUp() throws Exception {
    userDto = new UserDto();
    carDto = new CarDto();
    campusAccessDto = new CampusAccessDto();
    campusAccessDto.userDto = userDto;
    campusAccessDto.carDto = carDto;
    campusAccessDto.timePeriodDto = A_TIME_PERIOD_DTO;
    userService = mock(UserService.class);
    carService = mock(CarService.class);
    campusAccessRepository = mock(CampusAccessRepository.class);
    campusAccessFactory = mock(CampusAccessFactory.class);
    passRepository = mock(PassRepository.class);
    calendar = mock(Calendar.class);
    transactionFactory = mock(TransactionFactory.class);
    bankRepository = mock(BankRepository.class);
    campusAccessFeeRepository = mock(CampusAccessFeeRepository.class);
    campusAccessService = new CampusAccessService(userService,
                                                  carService,
                                                  campusAccessFactory,
                                                  campusAccessRepository,
                                                  passRepository,
                                                  calendar,
                                                  bankRepository,
                                                  campusAccessFeeRepository,
                                                  transactionFactory);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER_ID, A_CAR_ID, A_TIME_PERIOD);

    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCreateNewUser() {
    given(userService.createUser(userDto)).willReturn(A_USER);
    given(carService.createCar(carDto)).willReturn(A_CAR);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(userService, times(1)).createUser(userDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCreateNewCar() {
    given(userService.createUser(userDto)).willReturn(A_USER);
    given(carService.createCar(carDto)).willReturn(A_CAR);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(carService, times(1)).createCar(carDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallCampusAccessFactoryToCreateNewCampusAccess() {
    given(userService.createUser(userDto)).willReturn(A_USER);
    given(carService.createCar(carDto)).willReturn(A_CAR);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(campusAccessFactory, times(1)).create(A_USER_ID,
                                                 A_CAR_ID,
                                                 A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldSaveCampusAccessInRepository() {
    given(userService.createUser(userDto)).willReturn(A_USER);
    given(carService.createCar(carDto)).willReturn(A_CAR);
    given(campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_TIME_PERIOD_DTO)).willReturn(campusAccess);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(campusAccessRepository, times(1)).save(campusAccess);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldFindTheRightCampusAccessFromCode() {
    given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);
    given(calendar.now()).willReturn(A_START_DATE_TIME);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(campusAccessRepository, times(1)).findById(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldCallCalendarNow() {
    given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);
    given(calendar.now()).willReturn(A_START_DATE_TIME);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(calendar, times(1)).now();
  }

  @Test
  public void givenGrantedAccess_whenVerifyingIfCanAccessCampus_shouldCallPassRepositoryFind() {
    campusAccess.associatePass(A_PASS_CODE, A_TIME_PERIOD);
    given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);
    given(calendar.now()).willReturn(A_START_DATE_TIME);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(passRepository, times(1)).findByPassCode(A_PASS_CODE);
  }

  @Test
  public void givenGrantedAccess_whenVerifyingIfCanAccessCampus_shouldReturnTrue() {
    campusAccess.associatePass(A_PASS_CODE, A_TIME_PERIOD);
    given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);
    given(calendar.now()).willReturn(A_START_DATE_TIME);

    boolean result = campusAccessService.grantAccessToCampus(accessingCampusDto);

    assertThat(result).isTrue();
  }

  @Test
  public void givenAnUnregisteredCampusAccessCode_whenVerifyingIfCanAccessCampus_shouldNotGrantAccess() {
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willThrow(CampusAccessNotFoundException.class);

    boolean isGrantedAccess = campusAccessService.grantAccessToCampus(accessingCampusDto);

    assertThat(isGrantedAccess).isFalse();
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldFindCampusAccessInRepo() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);

    verify(campusAccessRepository).findById(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldAskCampusAccessToAssociatePass() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);

    verify(campusAccess).associatePass(A_PASS_CODE, A_TIME_PERIOD);
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldSaveCampusAccess() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);

    verify(campusAccessRepository).save(campusAccess);
  }
}