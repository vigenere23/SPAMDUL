package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidGenderException;
import ca.ulaval.glo4003.spamdul.usecases.parking.user.UserDto;
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

  private UserAssembler userAssembler;
  @Mock
  private CarAssembler carAssembler;
  private UserRequest userRequest;

  @Before
  public void setUp() throws Exception {
    userAssembler = new UserAssembler(carAssembler);
    userRequest = new UserRequest();
    userRequest.name = A_NAME;
    userRequest.birthDate = A_BIRTHDAY_DATE_STRING;
    userRequest.gender = A_GENDER_STRING;
  }

  @Test
  public void whenCreatingUserDto_shouldCreateUserDtoWithRightInfos() {
    UserDto userDto = userAssembler.fromRequest(userRequest);

    assertThat(userDto.name).isEqualTo(A_NAME);
    assertThat(userDto.birthDate).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(userDto.gender).isEqualTo(A_GENDER);
  }

  @Test(expected = InvalidGenderException.class)
  public void givenAWrongGender_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.gender = "Wrong gender";

    userAssembler.fromRequest(userRequest);
  }

  @Test(expected = InvalidBirthDateException.class)
  public void givenAWrongMonthBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "2020-1-10";

    userAssembler.fromRequest(userRequest);
  }

  @Test(expected = InvalidBirthDateException.class)
  public void givenAWrongDayBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "2020-01-1";

    userAssembler.fromRequest(userRequest);
  }

  @Test(expected = InvalidBirthDateException.class)
  public void givenAnInvalidFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "01-01-2020";

    userAssembler.fromRequest(userRequest);
  }
}