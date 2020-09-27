package ca.ulaval.glo4003.spamdul.infrastructure.db.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryInMemoryTest {

  private final User A_USER = new User(new UserId(),
                                       "Bob Ross",
                                       Gender.MALE,
                                       LocalDate.of(2004, 1, 1),
                                       DayOfWeek.MONDAY);

  private UserRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = new UserRepositoryInMemory();
  }

  @After
  public void cleanUp() {
    repository.clear();
  }

  @Test
  public void whenSavingNewUser_userShouldBeSaved() {
    repository.save(A_USER);

    assertThat(repository.findById(A_USER.getId())).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingById_shouldReturnTheRightUser() {
    repository.save(A_USER);

    User userById = repository.findById(A_USER.getId());

    assertThat(userById).isEqualTo(A_USER);
  }
}