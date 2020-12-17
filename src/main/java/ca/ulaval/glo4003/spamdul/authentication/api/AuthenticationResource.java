package ca.ulaval.glo4003.spamdul.authentication.api;

import ca.ulaval.glo4003.spamdul.authentication.api.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.usecases.AuthenticationUseCase;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/authentication")
public class AuthenticationResource {

  private final AuthenticationUseCase authenticationUseCase;

  public AuthenticationResource(AuthenticationUseCase authenticationUseCase) {
    this.authenticationUseCase = authenticationUseCase;
  }

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(LoginRequest loginRequest) {
    TemporaryToken temporaryToken = authenticationUseCase.login(loginRequest.username, loginRequest.hashedPassword);

    return Response.status(Status.OK)
                   .cookie(new NewCookie("accessToken",
                                         temporaryToken.toString(),
                                         "/",
                                         "",
                                         "",
                                         3600,
                                         false))
                   .build();
  }
}
