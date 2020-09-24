package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.user.UserDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

  private final String A_NAME = "Bob Ross";
  private final Gender A_GENDER = Gender.MALE;
  private final String A_GENDER_STRING = "male";
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(1945, 8, 10);
  private final String A_BIRTHDAY_DATE_STRING = "1945-08-10";
  private final String A_DAY_OF_THE_WEEK_STRING = "monday";
  private final DayOfWeek A_DAY_OF_THE_WEEK = DayOfWeek.MONDAY;

  private UserAssembler userAssembler;
  private UserRequest userRequest;

  @Before
  public void setUp() throws Exception {
    userAssembler = new UserAssembler();
    userRequest = new UserRequest();
    userRequest.name = A_NAME;
    userRequest.birthDate = A_BIRTHDAY_DATE_STRING;
    userRequest.gender = A_GENDER_STRING;
    userRequest.dayToAccessCampus = A_DAY_OF_THE_WEEK_STRING;
  }

  @Test
  public void whenCreatingUserDto_shouldCreateUserDtoWithRightInfos() {
    UserDto userDto = userAssembler.fromDto(userRequest);

    assertThat(userDto.name).isEqualTo(A_NAME);
    assertThat(userDto.birthDate).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(userDto.gender).isEqualTo(A_GENDER);
    assertThat(userDto.dayToAccessCampus).isEqualTo(A_DAY_OF_THE_WEEK);
  }

  @Test(expected = InvalidGenderArgumentException.class)
  public void givenAWrongGender_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.gender = "Wrong gender";

    userAssembler.fromDto(userRequest);
  }

  @Test(expected = InvalidBirthDateArgumentException.class)
  public void givenAWrongMonthBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "2020-1-10";

    userAssembler.fromDto(userRequest);
  }

  @Test(expected = InvalidBirthDateArgumentException.class)
  public void givenAWrongDayBirthDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "2020-01-1";

    userAssembler.fromDto(userRequest);
  }

  @Test(expected = InvalidBirthDateArgumentException.class)
  public void givenAnInvalidFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userRequest.birthDate = "01-01-2020";

    userAssembler.fromDto(userRequest);
  }

  @Test(expected = InvalidDayOfCampusAccessArgumentException.class)
  public void givenAnInvalidDayOfTheWeek_whenCreatingUser_shouldThrowInvalidDayOfCampusAccessException() {
    userRequest.dayToAccessCampus = "invalid";

    userAssembler.fromDto(userRequest);
  }
}