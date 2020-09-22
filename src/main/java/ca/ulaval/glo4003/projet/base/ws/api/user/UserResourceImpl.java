package ca.ulaval.glo4003.projet.base.ws.api.user;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.projet.base.ws.domain.user.User;
import ca.ulaval.glo4003.projet.base.ws.domain.user.UserService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UserResourceImpl implements UserResource {

  private final UserService userService;

  public UserResourceImpl(UserService userService) {
    this.userService = userService;
  }

  public Response addUser(UserDto userDto) {
    User createdUser = userService.addUser(userDto);

    return Response.status(Status.CREATED)
                   .header("Location", String.format("/users/%s", createdUser.getId()))
                   .build();
  }
}
