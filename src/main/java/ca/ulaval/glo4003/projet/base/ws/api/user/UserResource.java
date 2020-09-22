package ca.ulaval.glo4003.projet.base.ws.api.user;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public interface UserResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  Response addUser(UserDto userDto);
}
