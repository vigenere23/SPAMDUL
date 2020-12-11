package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.LoginRequest;
import ca.ulaval.glo4003.spamdul.usecases.authentification.AuthenticationService;
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

  private final AuthenticationService authenticationService;

  public AuthenticationResource(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(LoginRequest loginRequest) {
    TemporaryToken temporaryToken = authenticationService.login(loginRequest.username, loginRequest.hashedPassword);

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
