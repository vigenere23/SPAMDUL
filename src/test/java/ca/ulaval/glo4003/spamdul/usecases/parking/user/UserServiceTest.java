//package ca.ulaval.glo4003.spamdul.usecases.parking.car.user;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import ca.ulaval.glo4003.spamdul.entity.user.Gender;
//import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
//import java.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//
//public class UserServiceTest {
//
//  private final String A_NAME = "name";
//  private final Gender A_GENDER = Gender.MALE;
//  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
//
//  private UserFactory userFactory;
//  private UserService userService;
//  private UserDto userDto;
//
//
//  @Before
//  public void setUp() throws Exception {
//    userFactory = mock(UserFactory.class);
//    userService = new UserService(userFactory);
//
//    userDto = new UserDto();
//    userDto.name = A_NAME;
//    userDto.gender = A_GENDER;
//    userDto.birthDate = A_BIRTHDAY_DATE;
//  }
//
//  @Test
//  public void whenCreatingUser_shouldCallFactoryToCreateNewUser() {
//    userService.createUser(userDto);
//
//    verify(userFactory, times(1)).create(A_NAME, A_GENDER, A_BIRTHDAY_DATE);
//  }
//}