package ca.ulaval.glo4003.spamdul.usecases.authentification;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.RegisteredUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

  public static final String A_USERNAME = "username";
  public static final String AN_HASHED_PASSWORD = "hashed password";
  public static final AccessLevel AN_ACCESS_LEVEL = AccessLevel.ADMIN;
  public static final AccessLevel ANOTHER_ACCESS_LEVEL = AccessLevel.SSP_AGENT;

  private RegisteredUser registeredUser;
  private AuthenticationService authenticationService;
  private TemporaryToken temporaryToken;

  @Mock
  private AuthenticationRepository repository;

  @Before
  public void setUp() throws Exception {
    authenticationService = new AuthenticationService(repository);
    registeredUser = new RegisteredUser(A_USERNAME, AN_ACCESS_LEVEL);
    temporaryToken = new TemporaryToken();
  }

  @Test
  public void whenLoginIn_shouldCallRepositoryToFindRegisteredUser() {
    given(repository.findBy(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(registeredUser);

    authenticationService.login(A_USERNAME, AN_HASHED_PASSWORD);

    verify(repository, times(1)).findBy(A_USERNAME, AN_HASHED_PASSWORD);
  }

  @Test
  public void whenLoginIn_shouldReturnATemporaryToken() {
    given(repository.findBy(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(registeredUser);

    TemporaryToken temporaryToken = authenticationService.login(A_USERNAME, AN_HASHED_PASSWORD);

    assertThat(temporaryToken).isNotNull();
  }

  @Test
  public void whenLoginIn_shouldSaveTemporaryToken() {
    given(repository.findBy(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(registeredUser);

    TemporaryToken temporaryToken = authenticationService.login(A_USERNAME, AN_HASHED_PASSWORD);

    verify(repository, times(1)).save(temporaryToken, registeredUser);
  }

  @Test
  public void givenTheSameAccessLevel_whenVerifyingIfHasTheRightAccessLevel_shouldReturnTrue() {
    boolean hasTheRightAccessLevel = registeredUser.hasTheRightAccessLevel(AN_ACCESS_LEVEL);

    assertThat(hasTheRightAccessLevel).isTrue();
  }

  @Test
  public void whenFindingRegisteredUserBy_shouldFindRegisteredUserInRepository() {
    authenticationService.findRegisteredUser(temporaryToken);

    verify(repository, times(1)).findBy(temporaryToken);
  }
}