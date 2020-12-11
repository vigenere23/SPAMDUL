package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.entity.authentication.RegisteredUser;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import org.junit.Before;
import org.junit.Test;

public class InMemoryAuthenticationRepositoryTest {

  public static final String A_REGISTERED_USERNAME = "Karine";
  public static final String A_REGISTERED_HASHED_PASSWORD = "hashed_password";
  public static final AccessLevel AN_ACCESS_LEVEL = AccessLevel.ADMIN;

  private InMemoryAuthenticationRepository repository;
  private TemporaryToken temporaryToken;
  private RegisteredUser A_REGISTED_USER = new RegisteredUser(A_REGISTERED_USERNAME,
                                                              AN_ACCESS_LEVEL);

  @Before
  public void setUp() throws Exception {
    repository = new InMemoryAuthenticationRepository();
    temporaryToken = new TemporaryToken();
  }

  @Test
  public void givenTheRightCredentials_whenFindingRegisteredUser_shouldReturnTheRegisteredUser() {
    RegisteredUser registeredUser = repository.findBy(A_REGISTERED_USERNAME, A_REGISTERED_HASHED_PASSWORD);

    assertThat(registeredUser).isEqualTo(A_REGISTED_USER);
  }

  @Test(expected = InvalidCredentialsException.class)
  public void givenWrongUsername_whenFindingRegisteredUser_shouldThrowException() {
    repository.findBy("wrong username", A_REGISTERED_HASHED_PASSWORD);
  }

  @Test(expected = InvalidCredentialsException.class)
  public void givenWrongHashedPassword_whenFindingRegisteredUser_shouldThrowException() {
    repository.findBy(A_REGISTERED_USERNAME, "wrong hashed password");
  }

  @Test
  public void whenSavingTemporaryToken_shouldSaveToken() {
    repository.save(temporaryToken, A_REGISTED_USER);

    assertThat(repository.findBy(temporaryToken)).isEqualTo(A_REGISTED_USER);
  }

  @Test
  public void whenFindingByTemporaryToken_shouldReturnTheRightRegisteredUser() {
    repository.save(temporaryToken, A_REGISTED_USER);

    RegisteredUser registeredUser = repository.findBy(temporaryToken);

    assertThat(registeredUser).isEqualTo(A_REGISTED_USER);
  }

  @Test(expected = NoRegisteredUserLoggedInException.class)
  public void givenNoRegisteredUserLoggedIn_whenFindingByTemporaryToken_shouldThrowException() {
    repository.findBy(temporaryToken);
  }
}