package ca.ulaval.glo4003.projet.base.ws.entity.user;

import static com.google.common.truth.Truth.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Test;

public class UserTest {

  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(1991, 7, 10);
  private final DayOfWeek A_DAY_TO_ACCESS_CAMPUS = DayOfWeek.MONDAY;

  @Test
  public void whenCreatingNewUser_shouldCreateRandomId() {
    User user = new User(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_DAY_TO_ACCESS_CAMPUS);

    assertThat(user.getId()).isNotNull();
  }

  @Test
  public void givenABirthDate_whenCalculatingAge_shouldReturnTheCorrectAge() {
    User user = new User(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_DAY_TO_ACCESS_CAMPUS);
    LocalDate TODAY_DATE = LocalDate.of(2020, 9, 22);

    int age = user.getAge(TODAY_DATE);

    int AN_AGE = 29;
    assertThat(age).isEqualTo(AN_AGE);
  }
}