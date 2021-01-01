package ca.ulaval.glo4003.spamdul.authentication.usecases;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.authentication.entities.AccessLevel;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.RegisteredUser;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationUseCaseTest {

  public static final String A_USERNAME = "username";
  public static final String AN_HASHED_PASSWORD = "hashed password";
  public static final AccessLevel AN_ACCESS_LEVEL = AccessLevel.ADMIN;
  public static final AccessLevel ANOTHER_ACCESS_LEVEL = AccessLevel.SSP_AGENT;

  private RegisteredUser registeredUser;
  private AuthenticationUseCase authenticationUseCase;
  private TemporaryToken temporaryToken;

  @Mock
  private AuthenticationRepository repository;

  @Before
  public void setUp() throws Exception {
    authenticationUseCase = new AuthenticationUseCase(repository);
    registeredUser = new RegisteredUser(A_USERNAME, AN_ACCESS_LEVEL);
    temporaryToken = new TemporaryToken();
  }

  @Test
  public void whenLoginIn_shouldCallRepositoryToFindRegisteredUser() {
    given(repository.findBy(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(registeredUser);

    authenticationUseCase.login(A_USERNAME, AN_HASHED_PASSWORD);

    verify(repository).findBy(A_USERNAME, AN_HASHED_PASSWORD);
  }

  @Test
  public void whenLoginIn_shouldReturnATemporaryToken() {
    given(repository.findBy(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(registeredUser);

    TemporaryToken temporaryToken = authenticationUseCase.login(A_USERNAME, AN_HASHED_PASSWORD);

    assertThat(temporaryToken).isNotNull();
  }

  @Test
  public void whenLoginIn_shouldSaveTemporaryToken() {
    given(repository.findBy(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(registeredUser);

    TemporaryToken temporaryToken = authenticationUseCase.login(A_USERNAME, AN_HASHED_PASSWORD);

    verify(repository).save(temporaryToken, registeredUser);
  }

  @Test
  public void givenTheSameAccessLevel_whenVerifyingIfHasTheRightAccessLevel_shouldReturnTrue() {
    boolean hasTheRightAccessLevel = registeredUser.hasTheRightAccessLevel(AN_ACCESS_LEVEL);

    assertThat(hasTheRightAccessLevel).isTrue();
  }

  @Test
  public void whenFindingRegisteredUserBy_shouldFindRegisteredUserInRepository() {
    authenticationUseCase.findRegisteredUser(temporaryToken);

    verify(repository).findBy(temporaryToken);
  }
}
