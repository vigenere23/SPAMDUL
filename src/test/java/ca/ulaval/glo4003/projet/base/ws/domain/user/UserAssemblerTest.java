package ca.ulaval.glo4003.projet.base.ws.domain.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

  private UserAssembler userAssembler;
  private UserDto userDto;
  private String A_NAME;
  private Gender A_GENDER;
  private String A_GENDER_STRING;
  private LocalDate A_BIRTHDAY_DATE;
  private String A_BIRTHDAY_DATE_STRING;
  private String A_DAY_OF_THE_WEEK_STRING;
  private DayOfWeek A_DAY_OF_THE_WEEK;

  @Before
  public void setUp() throws Exception {
    userAssembler = new UserAssembler();
    A_NAME = "Bob Ross";
    A_GENDER = Gender.MALE;
    A_GENDER_STRING = "male";
    A_BIRTHDAY_DATE = LocalDate.of(1945, 8, 10);
    A_BIRTHDAY_DATE_STRING = "1945-08-10";
    A_DAY_OF_THE_WEEK_STRING = "monday";
    A_DAY_OF_THE_WEEK = DayOfWeek.MONDAY;
    userDto = new UserDto();
    userDto.name = A_NAME;
    userDto.birthdayDate = A_BIRTHDAY_DATE_STRING;
    userDto.gender = A_GENDER_STRING;
    userDto.dayToAccessCampus = A_DAY_OF_THE_WEEK_STRING;

  }

  @Test
  public void whenCreatingUser_shouldCreateUserWithRightInformations() {
    User user = userAssembler.create(userDto);

    assertThat(user.getName()).isEqualTo(A_NAME);
    assertThat(user.getBirthday()).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(user.getGender()).isEqualTo(A_GENDER);
    assertThat(user.getDayToAccessCampus()).isEqualTo(A_DAY_OF_THE_WEEK);
  }

  @Test(expected = InvalidGenderArgumentException.class)
  public void givenAWrongGender_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userDto.gender = "Wrong gender";

    userAssembler.create(userDto);
  }

  @Test(expected = InvalidBirthdayDateArgumentException.class)
  public void givenAWrongMonthBirthdayDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userDto.birthdayDate = "2020-1-10";

    userAssembler.create(userDto);
  }

  @Test(expected = InvalidBirthdayDateArgumentException.class)
  public void givenAWrongDayBirthdayDateFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userDto.birthdayDate = "2020-01-1";

    userAssembler.create(userDto);
  }

  @Test(expected = InvalidBirthdayDateArgumentException.class)
  public void givenAnInvalidFormat_whenCreatingUser_shouldThrowIllegalArgumentException() {
    userDto.birthdayDate = "01-01-2020";

    userAssembler.create(userDto);
  }

  @Test(expected = InvalidDayOfCampusAccessArgumentException.class)
  public void givenAnInvalidDayOfTheWeek_whenCreatingUser_shouldThrowInvalidDayOfCampusAccessException() {
    userDto.dayToAccessCampus = "invalid";

    userAssembler.create(userDto);
  }

  @Test(expected = InvalidDayOfCampusAccessArgumentException.class)
  public void givenSaturdayAsAccessDay_whenCreatingUser_shouldThrowInvalidDayOfCampusAccessException() {
    userDto.dayToAccessCampus = "saturday";

    userAssembler.create(userDto);
  }

  @Test(expected = InvalidDayOfCampusAccessArgumentException.class)
  public void givenSundayAsAccessDay_whenCreatingUser_shouldThrowInvalidDayOfCampusAccessException() {
    userDto.dayToAccessCampus = "sunday";

    userAssembler.create(userDto);
  }
}