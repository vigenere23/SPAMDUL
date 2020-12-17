package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.Car;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.car.CarDto;
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
    when(carFactory.create(CAR_TYPE, BRAND, MODEL, YEAR, LICENSE_PLATE)).thenReturn(A_CAR);
    when(userIdFactory.create()).thenReturn(A_USER_ID);

    User user = userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, carDto);

    assertThat(user.getName()).isEqualTo(A_NAME);
    assertThat(user.getGender()).isEqualTo(A_GENDER);
    assertThat(user.getBirthDate()).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(user.getCar()).isEqualTo(A_CAR);
    assertThat(user.getId()).isEqualTo(A_USER_ID);
  }

  @Test
  public void givenNoCar_whenCreatingUser_shouldCreateUserWithoutACar() {
    carDto = null;
    when(userIdFactory.create()).thenReturn(A_USER_ID);

    User user = userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, carDto);

    assertThat(user.getCar()).isNull();
    verify(carFactory, never()).create(any(CarType.class), anyString(), anyString(), anyInt(), anyString());
  }
}
