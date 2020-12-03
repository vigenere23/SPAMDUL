package ca.ulaval.glo4003.spamdul.entity.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
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
  CarFactory carFactory;

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
    userFactory = new UserFactory(carFactory);
  }

  @Test
  public void whenCreatingUser_shouldCreateUserWithTheRightInfos() {
    User user = userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, carDto);

    assertThat(user.getName()).isEqualTo(A_NAME);
    assertThat(user.getGender()).isEqualTo(A_GENDER);
    assertThat(user.getBirthDate()).isEqualTo(A_BIRTHDAY_DATE);
    verify(carFactory, times(1)).create(CAR_TYPE, BRAND, MODEL, YEAR, LICENSE_PLATE);
  }
}