package ca.ulaval.glo4003.spamdul.infrastructure.ui.user;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.user.UserService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/users")
public class UserResource {

  private final UserAssembler userAssembler;
  private final UserService userService;

  public UserResource(UserAssembler userAssembler,
                      UserService userService) {

    this.userAssembler = userAssembler;
    this.userService = userService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createNewUser(UserRequest userRequest) {
    //TODO a tester
    UserDto userDto = userAssembler.fromRequest(userRequest);
    UserId newUserId = userService.createUser(userDto);

    UserResponse userResponse = new UserResponse();
    userResponse.userId = newUserId.toString();

    return Response.status(Status.CREATED).entity(userResponse).build();
  }
}
