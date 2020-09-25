package ca.ulaval.glo4003.spamdul.entity.user;

import static com.google.common.truth.Truth.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UserFactoryTest {

  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final DayOfWeek A_DAY_TO_ACCESS_CAMPUS = DayOfWeek.MONDAY;

  private UserFactory userFactory;

  @Before
  public void setUp() throws Exception {
    userFactory = new UserFactory();
  }

  @Test
  public void whenCreatingUser_shouldCreateUserWithTheRightInfos() {
    User user = userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_DAY_TO_ACCESS_CAMPUS);

    assertThat(user.getName()).isEqualTo(A_NAME);
    assertThat(user.getGender()).isEqualTo(A_GENDER);
    assertThat(user.getBirthDate()).isEqualTo(A_BIRTHDAY_DATE);
    assertThat(user.getDayToAccessCampus()).isEqualTo(A_DAY_TO_ACCESS_CAMPUS);
  }

  @Test(expected = InvalidDayToAccessCampusException.class)
  public void givenSaturdayAsDayToAccessCampus_whenCreatingUser_shouldThrowInvalidDayToAccessCampus() {
    userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, DayOfWeek.SATURDAY);
  }

  @Test(expected = InvalidDayToAccessCampusException.class)
  public void givenSundayAsDayToAccessCampus_whenCreatingUser_shouldThrowInvalidDayToAccessCampus() {
    userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE, DayOfWeek.SUNDAY);
  }
}