package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.*;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.*;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CampusAccessServiceTest {

  private static final PassCode A_PASS_CODE = new PassCode();
  private static final PassType A_PASS_TYPE = PassType.MONTHLY;
  private static final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.SATURDAY;
  private static final UserId A_USER_ID = new UserId();
  private static final CarId A_CAR_ID = new CarId();
  private static final User A_USER = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1));
  private static final Car A_CAR = new Car(A_CAR_ID, CarType.GOURMANDE, "brand", "model", 2020, "xxx xxx");
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final Period A_PERIOD = Period.SEMESTER_1;
  private static final DayOfWeek A_CAMPUS_ACCESS_DAY = DayOfWeek.FRIDAY;
  private static final LocalDate A_CAMPUS_ACCESS_DATE = LocalDate.of(2020, 9, 25);
  private static final CampusAccess A_CAMPUS_ACCESS = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER_ID, A_CAR_ID, A_DAY_OF_WEEK, A_PERIOD);
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);

  private CampusAccessRepository campusAccessRepository;
  private UserService userService;
  private CarService carService;
  private CampusAccessService campusAccessService;
  private CampusAccessFactory campusAccessFactory;
  private CampusAccessDto campusAccessDto;
  private UserDto userDto;
  private CarDto carDto;
  private AccessingCampusDto accessingCampusDto;
  private ParkingAccessLogRepository parkingAccessLogRepository;
  private PassRepository passRepository;

  @Before
  public void setUp() throws Exception {
    userDto = new UserDto();
    carDto = new CarDto();
    campusAccessDto = new CampusAccessDto();
    campusAccessDto.userDto = userDto;
    campusAccessDto.carDto = carDto;
    campusAccessDto.period = A_PERIOD;
    campusAccessDto.dayToAccessCampus = A_CAMPUS_ACCESS_DAY;
    userService = mock(UserService.class);
    carService = mock(CarService.class);
    campusAccessRepository = mock(CampusAccessRepository.class);
    campusAccessFactory = mock(CampusAccessFactory.class);
    parkingAccessLogRepository = mock(ParkingAccessLogRepository.class);
    passRepository = mock(PassRepository.class);
    campusAccessService = new CampusAccessService(userService,
                                                  carService,
                                                  campusAccessFactory,
                                                  campusAccessRepository,
                                                  passRepository);

    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.accessingCampusDate = A_CAMPUS_ACCESS_DATE;
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;
    A_CAMPUS_ACCESS.setAssociatedPassCode(A_PASS_CODE);
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
                                                 campusAccessDto.period,
                                                 campusAccessDto.dayToAccessCampus);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldSaveCampusAccessInRepository() {
    given(userService.createUser(userDto)).willReturn(A_USER);
    given(carService.createCar(carDto)).willReturn(A_CAR);
    given(campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, A_CAMPUS_ACCESS_DAY)).willReturn(A_CAMPUS_ACCESS);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(campusAccessRepository, times(1)).save(A_CAMPUS_ACCESS);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldFindTheRightCampusAccessFromCode() {
    given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(A_CAMPUS_ACCESS);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(campusAccessRepository, times(1)).findById(A_CAMPUS_ACCESS_CODE);
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

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_PASS_TYPE, A_DAY_OF_WEEK);

    verify(campusAccessRepository).findById(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldAskCampusAccessToAssociatePass() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_PASS_TYPE, A_DAY_OF_WEEK);

    verify(campusAccess).associatePass(A_PASS_CODE, A_PASS_TYPE, A_DAY_OF_WEEK);
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldSaveCampusAccess() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_PASS_TYPE, A_DAY_OF_WEEK);

    verify(campusAccessRepository).save(campusAccess);
  }
}