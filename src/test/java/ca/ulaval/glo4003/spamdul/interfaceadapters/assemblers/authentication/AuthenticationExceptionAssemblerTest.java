package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.usecases.infraction.UnauthorizedUserException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationExceptionAssemblerTest {

  private AuthenticationExceptionAssembler assembler;

  @Before
  public void setUp() throws Exception {
    assembler = new AuthenticationExceptionAssembler();
  }

  @Test
  public void givenAnUnauthorizedUserException_whenAssembling_shouldReturnResponseWithCorrectResponse() {
    Response response = assembler.toResponse(new UnauthorizedUserException());

    assertThat(response.getStatus()).isEqualTo(Status.FORBIDDEN.getStatusCode());
  }

  @Test
  public void givenNoRegisteredUserLoggedIn_whenAssembling_shouldReturnCorrectResponse() {
    Response response = assembler.toResponse(new NoRegisteredUserLoggedInException());

    assertThat(response.getStatus()).isEqualTo(Status.UNAUTHORIZED.getStatusCode());
  }
}