package ca.ulaval.glo4003.projet.base.ws.infrastructure.user;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.projet.base.ws.domain.user.User;
import ca.ulaval.glo4003.projet.base.ws.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryInMemoryTest {

  private UserRepository repository;
  private User A_USER;

  @Before
  public void setUp() throws Exception {
    repository = new UserRepositoryInMemory();
    A_USER = new User();
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