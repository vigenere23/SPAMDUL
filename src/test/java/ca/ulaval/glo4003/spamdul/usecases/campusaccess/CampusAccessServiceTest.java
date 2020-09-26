package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.Period;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import java.time.DayOfWeek;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessServiceTest {

  private final UserId A_USER_ID = new UserId();
  private final CarId A_CAR_ID = new CarId();
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private final Period A_PERIOD = Period.SEMESTER_1;
  private final DayOfWeek A_CAMPUS_ACCESS_DAY = DayOfWeek.WEDNESDAY;
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
    campusAccessService = new CampusAccessService(userService, carService, campusAccessFactory, campusAccessRepository);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCreateNewUser() {
    campusAccessService.createNewCampusAccess(campusAccessDto);

    verify(userService, times(1)).createUser(userDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCreateNewCar() {
    campusAccessService.createNewCampusAccess(campusAccessDto);

    verify(carService, times(1)).createCar(carDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallCarFactoryToCreateNewCampusAccess() {
    given(userService.createUser(userDto)).willReturn(A_USER_ID);
    given(carService.createCar(carDto)).willReturn(A_CAR_ID);

    campusAccessService.createNewCampusAccess(campusAccessDto);

    verify(campusAccessFactory, times(1)).create(A_USER_ID,
                                                 A_CAR_ID,
                                                 campusAccessDto.period,
                                                 campusAccessDto.dayToAccessCampus);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldSaveCampusAccessInRepository() {
    given(userService.createUser(userDto)).willReturn(A_USER_ID);
    given(carService.createCar(carDto)).willReturn(A_CAR_ID);
    given(campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, A_CAMPUS_ACCESS_DAY)).willReturn(A_CAMPUS_ACCESS);

    campusAccessService.createNewCampusAccess(campusAccessDto);

    verify(campusAccessRepository, times(1)).save(A_CAMPUS_ACCESS);
  }
}