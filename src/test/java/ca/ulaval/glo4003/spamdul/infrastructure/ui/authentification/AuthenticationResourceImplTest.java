package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.AuthenticationResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;
import ca.ulaval.glo4003.spamdul.usecases.authentification.TemporaryToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationResourceImplTest {

  public static final String A_USERNAME = "username";
  public static final String AN_HASHED_PASSWORD = "hashed password";
  public static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  @Mock
  AuthenticationService service;

  AuthenticationResourceImpl authenticationResource;
  private LoginRequest loginRequest;

  @Before
  public void setUp() throws Exception {
    authenticationResource = new AuthenticationResourceImpl(service);
    loginRequest = new LoginRequest();
    loginRequest.userName = A_USERNAME;
    loginRequest.hashedPassword = AN_HASHED_PASSWORD;
  }

  @Test
  public void whenLoginIn_shouldCallServiceToLoginIn() {
    given(service.login(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(A_TEMPORARY_TOKEN);
    authenticationResource.login(loginRequest);

    verify(service, times(1)).login(A_USERNAME, AN_HASHED_PASSWORD);
  }

  @Test
  public void whenLoginIn_shouldReturnResponseWithTheRightTemporaryToken() {
    given(service.login(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(A_TEMPORARY_TOKEN);

    AuthenticationResponse authenticationResponse = authenticationResource.login(loginRequest);

    assertThat(authenticationResponse.temporaryToken).isEqualTo(A_TEMPORARY_TOKEN.toString());
  }
}