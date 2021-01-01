package ca.ulaval.glo4003.spamdul.assemblers.user;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidGenderException;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.Gender;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserCreationDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserAssemblerTest {

  private final String A_NAME = "Bob Ross";
  private final Gender A_GENDER = Gender.MALE;
  private final String A_GENDER_STRING = "male";
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(1945, 8, 10);
  private final String A_BIRTHDAY_DATE_STRING = "1945-08-10";

  private CarRequest carRequest;
  private UserCreationRequest userCreationRequest;
  private UserAssembler userAssembler;
  @Mock
  private CarAssembler carAssembler;

  @Before
  public void setUp() throws Exception {
    carRequest = new CarRequest();
    userAssembler = new UserAssembler(carAssembler);
    userCreationRequest = new UserCreationRequest();

    userCreationRequest.name = A_NAME;
    userCreationRequest.birthDate = A_BIRTHDAY_DATE_STRING;
    userCreationRequest.gender = A_GENDER_STRING;
    userCreationRequest.car = carRequest;
  }

  @Test
  public void whenCreatingUserDto_shouldCallCarAssembler() {
    userAssembler.fromRequest(userCreationRequest);

    verify(carAssembler).fromRequest(carRequest);
  }

  @Test
  public void whenCreatingUserDto_shouldCreateUserDtoWithRightInfos() {
    UserCreationDto userCreationDto = userAssembler.fromRequest(userCreationRequest);

    assertThat(userCreationDto.name).isEqualTo(A_NAME);
    assertThat(userCreationDto.birthDate).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(userCreationDto.gender).isEqualTo(A_GENDER);
  }

  @Test
  public void givenNoCarInRequest_whenCreatingUserDto_shouldNotSetCarInfos() {
    userCreationRequest.car = null;

    UserCreationDto userCreationDto = userAssembler.fromRequest(userCreationRequest);

    assertThat(userCreationDto.carDto).isNull();
  }

  @Test(expected = InvalidGenderException.class)
  public void givenAWrongGender_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userCreationRequest.gender = "Wrong gender";

    userAssembler.fromRequest(userCreationRequest);
  }

  @Test(expected = InvalidBirthDateException.class)
  public void givenAWrongMonthBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userCreationRequest.birthDate = "2020-1-10";

    userAssembler.fromRequest(userCreationRequest);
  }

  @Test(expected = InvalidBirthDateException.class)
  public void givenAWrongDayBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userCreationRequest.birthDate = "2020-01-1";

    userAssembler.fromRequest(userCreationRequest);
  }

  @Test(expected = InvalidBirthDateException.class)
  public void givenAnInvalidFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userCreationRequest.birthDate = "01-01-2020";

    userAssembler.fromRequest(userCreationRequest);
  }
}
