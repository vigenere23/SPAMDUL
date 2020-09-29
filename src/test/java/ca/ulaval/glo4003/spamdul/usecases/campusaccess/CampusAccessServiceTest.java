package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessServiceTest {

  private final UserId A_USER_ID = new UserId();
  private final CarId A_CAR_ID = new CarId();
  private final User A_USER = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1));
  private final Car A_CAR = new Car(A_CAR_ID, CarType.GOURMANDE, "brand", "model", 2020, "xxx xxx");
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private final Period A_PERIOD = Period.SEMESTER_1;
  private final DayOfWeek A_CAMPUS_ACCESS_DAY = DayOfWeek.FRIDAY;
  private final LocalDate A_CAMPUS_ACCESS_DATE = LocalDate.of(2020, 9, 25);
  private final CampusAccess A_CAMPUS_ACCESS = new CampusAccess(A_CAMPUS_ACCESS_CODE,
                                                                A_USER_ID,
                                                                A_CAR_ID,
                                                                A_CAMPUS_ACCESS_DAY,
                                                                A_PERIOD);

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
    campusAccessService = new CampusAccessService(userService,
                                                  carService,
                                                  campusAccessFactory,
                                                  campusAccessRepository);

    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.accessingCampusDate = A_CAMPUS_ACCESS_DATE;
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
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willReturn(A_CAMPUS_ACCESS);
    campusAccessService.canAccessCampus(accessingCampusDto);

    verify(campusAccessRepository, times(1)).findById(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void givenAnUnregisteredCampusAccessCode_whenVerifyingIfCanAccessCampus_shouldNotGrantAccess() {
    given(campusAccessRepository.findById(A_CAMPUS_ACCESS_CODE)).willThrow(CampusAccessNotFoundException.class);

    boolean isGrantedAccess = campusAccessService.canAccessCampus(accessingCampusDto);

    assertThat(isGrantedAccess).isFalse();
  }
}