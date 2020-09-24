package ca.ulaval.glo4003.spamdul.infrastructure.ui.user;

import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.user.UserService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UserResourceImpl implements UserResource {

  private final UserService userService;
  private final UserAssembler userAssembler;

  public UserResourceImpl(UserService userService,
                          UserAssembler userAssembler) {
    this.userService = userService;
    this.userAssembler = userAssembler;
  }

  public Response addUser(UserRequest userRequest) {
    UserDto userDto = userAssembler.fromDto(userRequest);
    User createdUser = userService.createUser(userDto);

    return Response.status(Status.CREATED)
                   .header("Location", String.format("/users/%s", createdUser.getId()))
                   .build();
  }
}
