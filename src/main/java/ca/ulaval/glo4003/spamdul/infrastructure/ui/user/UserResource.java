package ca.ulaval.glo4003.spamdul.infrastructure.ui.user;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface UserResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  Response addUser(UserRequest userRequest);
}
