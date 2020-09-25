package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

  private final String A_NAME = "Bob Ross";
  private final Gender A_GENDER = Gender.MALE;
  private final String A_GENDER_STRING = "male";
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(1945, 8, 10);
  private final String A_BIRTHDAY_DATE_STRING = "1945-08-10";

  private UserAssembler userAssembler;
  private UserRequest userRequest;

  @Before
  public void setUp() throws Exception {
    userAssembler = new UserAssembler();
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

  @Test(expected = InvalidGenderArgumentException.class)
  public void givenAWrongGender_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.gender = "Wrong gender";

    userAssembler.fromRequest(userRequest);
  }

  @Test(expected = InvalidBirthDateArgumentException.class)
  public void givenAWrongMonthBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "2020-1-10";

    userAssembler.fromRequest(userRequest);
  }

  @Test(expected = InvalidBirthDateArgumentException.class)
  public void givenAWrongDayBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "2020-01-1";

    userAssembler.fromRequest(userRequest);
  }

  @Test(expected = InvalidBirthDateArgumentException.class)
  public void givenAnInvalidFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "01-01-2020";

    userAssembler.fromRequest(userRequest);
  }
}