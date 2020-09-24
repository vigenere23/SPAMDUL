package ca.ulaval.glo4003.projet.base.ws.usecases.user;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.projet.base.ws.entity.user.Gender;
import ca.ulaval.glo4003.projet.base.ws.entity.user.User;
import ca.ulaval.glo4003.projet.base.ws.entity.user.UserFactory;
import ca.ulaval.glo4003.projet.base.ws.entity.user.UserId;
import ca.ulaval.glo4003.projet.base.ws.entity.user.UserRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

  private final UserId A_USER_ID = new UserId();
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final DayOfWeek A_DAY_TO_ACCESS_CAMPUS = DayOfWeek.MONDAY;
  private final User user = new User(A_USER_ID,A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_DAY_TO_ACCESS_CAMPUS);

  private UserRepository userRepository;
  private UserFactory userFactory;
  private UserService userService;
  private UserDto userDto;


  @Before
  public void setUp() throws Exception {
    userRepository = mock(UserRepository.class);
    userFactory = mock(UserFactory.class);
    userService = new UserService(userFactory, userRepository);

    userDto = new UserDto();
    userDto.name = A_NAME;
    userDto.gender = A_GENDER;
    userDto.birthDate = A_BIRTHDAY_DATE;
    userDto.dayToAccessCampus = A_DAY_TO_ACCESS_CAMPUS;
  }

  @Test
  public void whenCreatingUser_shouldCallFactoryToCreateNewUser() {
    userService.createUser(userDto);

    verify(userFactory, times(1)).create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_DAY_TO_ACCESS_CAMPUS);
  }

  @Test
  public void whenCreatingNewUser_shouldAddUserToRepository() {
    given(userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_DAY_TO_ACCESS_CAMPUS)).willReturn(user);

    userService.createUser(userDto);

    verify(userRepository, times(1)).save(user);
  }
}