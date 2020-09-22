package ca.ulaval.glo4003.projet.base.ws.domain.user;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class UserTest {

  private LocalDate A_BIRTHDAY_DATE = LocalDate.of(1991, 7, 10);
  private LocalDate TODAYS_DATE = LocalDate.of(2020, 8, 21);
  private int AN_AGE = 29;

  @Test
  public void whenCreatingNewUser_shouldCreateRandomId() {
    User user = new User();

    assertThat(user.getId()).isNotNull();
  }

  @Test
  public void givenABirthdayDate_whenCalculatingAge_shouldReturnTheCorrectAge() {
    User user = new User();
    user.setBirthday(A_BIRTHDAY_DATE);

    int age = user.getAge(TODAYS_DATE);

    assertThat(age).isEqualTo(AN_AGE);
  }
}