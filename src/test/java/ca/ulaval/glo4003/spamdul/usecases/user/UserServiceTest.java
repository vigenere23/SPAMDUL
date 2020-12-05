package ca.ulaval.glo4003.spamdul.usecases.user;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.usecases.user.car.CarDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  public static final UserId USER_ID = UserId.valueOf("123");
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final CarDto A_CAR_DTO = new CarDto();

  private UserService userService;
  private UserDto userDto;

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserFactory userFactory;
  @Mock
  private User user;


  @Before
  public void setUp() throws Exception {
    userService = new UserService(userRepository, userFactory);

    userDto = new UserDto();
    userDto.name = A_NAME;
    userDto.gender = A_GENDER;
    userDto.birthDate = A_BIRTHDAY_DATE;
    userDto.carDto = A_CAR_DTO;

    when(user.getId()).thenReturn(USER_ID);
    when(userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_CAR_DTO)).thenReturn(user);
  }

  @Test
  public void whenCreatingUser_shouldCallFactoryToCreateNewUser() {
    userService.createUser(userDto);

    verify(userFactory, times(1)).create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_CAR_DTO);
  }
}
