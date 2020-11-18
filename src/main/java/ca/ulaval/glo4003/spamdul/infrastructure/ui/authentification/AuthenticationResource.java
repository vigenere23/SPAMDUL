package ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.AuthenticationResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.dto.LoginRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/authentication")
public interface AuthenticationResource {

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  AuthenticationResponse login(LoginRequest loginRequest);
}
