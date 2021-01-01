package ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.Gender;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.car.CarDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserUseCaseTest {

  public static final UserId USER_ID = UserId.valueOf("123");
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final CarDto A_CAR_DTO = new CarDto();

  private UserUseCase userUseCase;
  private UserCreationDto userCreationDto;

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserFactory userFactory;
  @Mock
  private User user;


  @Before
  public void setUp() throws Exception {
    userUseCase = new UserUseCase(userRepository, userFactory);

    userCreationDto = new UserCreationDto();
    userCreationDto.name = A_NAME;
    userCreationDto.gender = A_GENDER;
    userCreationDto.birthDate = A_BIRTHDAY_DATE;
    userCreationDto.carDto = A_CAR_DTO;

    when(user.getId()).thenReturn(USER_ID);
    when(userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_CAR_DTO)).thenReturn(user);
  }

  @Test
  public void whenCreatingUser_shouldCallFactoryToCreateNewUser() {
    userUseCase.createUser(userCreationDto);

    verify(userFactory).create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_CAR_DTO);
  }
}
