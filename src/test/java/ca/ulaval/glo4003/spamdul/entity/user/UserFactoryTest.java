package ca.ulaval.glo4003.spamdul.entity.user;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class UserFactoryTest {

  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);

  private UserFactory userFactory;

  @Before
  public void setUp() throws Exception {
    userFactory = new UserFactory(carFactory);
  }

  @Test
  public void whenCreatingUser_shouldCreateUserWithTheRightInfos() {
    User user = userFactory.create(A_NAME, A_GENDER, A_BIRTHDAY_DATE);

    assertThat(user.getName()).isEqualTo(A_NAME);
    assertThat(user.getGender()).isEqualTo(A_GENDER);
    assertThat(user.getBirthDate()).isEqualTo(A_BIRTHDAY_DATE);
  }
}