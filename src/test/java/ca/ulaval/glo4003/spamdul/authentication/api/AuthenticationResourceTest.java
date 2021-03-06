package ca.ulaval.glo4003.spamdul.authentication.api;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.authentication.api.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.usecases.AuthenticationUseCase;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationResourceTest {

  public static final String A_USERNAME = "username";
  public static final String AN_HASHED_PASSWORD = "hashed password";
  public static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();

  @Mock
  AuthenticationUseCase service;

  AuthenticationResource authenticationResource;
  private LoginRequest loginRequest;

  @Before
  public void setUp() throws Exception {
    authenticationResource = new AuthenticationResource(service);
    loginRequest = new LoginRequest();
    loginRequest.username = A_USERNAME;
    loginRequest.hashedPassword = AN_HASHED_PASSWORD;
  }

  @Test
  public void whenLoginIn_shouldCallServiceToLoginIn() {
    given(service.login(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(A_TEMPORARY_TOKEN);

    authenticationResource.login(loginRequest);

    verify(service).login(A_USERNAME, AN_HASHED_PASSWORD);
  }

  @Test
  public void whenLoginIn_shouldReturnResponseWithTheRightTemporaryToken() {
    given(service.login(A_USERNAME, AN_HASHED_PASSWORD)).willReturn(A_TEMPORARY_TOKEN);

    Response authenticationResponse = authenticationResource.login(loginRequest);

    assertThat(authenticationResponse.getCookies()
                                     .get("accessToken")
                                     .getValue()).isEqualTo(A_TEMPORARY_TOKEN.toString());
  }
}
