package ca.ulaval.glo4003.spamdul.entity.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserFactoryTest {

  public static final CarType CAR_TYPE = CarType.ECONOMIQUE;
  public static final String BRAND = "brand";
  public static final String MODEL = "model";
  public static final int YEAR = 2020;
  public static final String LICENSE_PLATE = "xxx xxx";
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private UserFactory userFactory;
  private UserDto userDto;
  private CarDto carDto;

  @Mock
  private CarFactory carFactory;
  @Mock
  private UserIdFactory userIdFactory;
  @Mock
  private Car A_CAR;
  @Mock
  private UserId A_USER_ID;

  @Before
  public void setUp() throws Exception {
    carDto = new CarDto();
    carDto.year = YEAR;
    carDto.carType = CAR_TYPE;
    carDto.brand = BRAND;
    carDto.model = MODEL;
    carDto.licensePlate = LICENSE_PLATE;

    userDto = new UserDto();
    userDto.carDto = carDto;
    userDto.birthDate = A_BIRTHDAY_DATE;
    userDto.gender = A_GENDER;
    userDto.name = A_NAME;
    userFactory = new UserFactory(userIdFactory, carFactory);
  }

  @Test
  public void whenCreatingUser_shouldCreateUserWithTheRightInfos() {
    when(userIdFactory.create()).thenReturn(A_USER_ID);
    when(carFactory.create(carDto)).thenReturn(A_CAR);

    User user = userFactory.create(userDto);

    assertThat(user.getName()).isEqualTo(A_NAME);
    assertThat(user.getGender()).isEqualTo(A_GENDER);
    assertThat(user.getBirthDate()).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(user.getCar()).isEqualTo(A_CAR);
    assertThat(user.getId()).isEqualTo(A_USER_ID);
  }
}
