package ca.ulaval.glo4003.spamdul.parking.api.parkinguser;

import ca.ulaval.glo4003.spamdul.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.parking.api.parkinguser.dto.UserResponse;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserUseCase;
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
  private final UserUseCase userUseCase;

  public UserResource(UserAssembler userAssembler,
                      UserUseCase userUseCase) {

    this.userAssembler = userAssembler;
    this.userUseCase = userUseCase;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createNewUser(UserRequest userRequest) {
    UserDto userDto = userAssembler.fromRequest(userRequest);
    UserId newUserId = userUseCase.createUser(userDto);

    UserResponse userResponse = new UserResponse();
    userResponse.userId = newUserId.toString();

    return Response.status(Status.CREATED).entity(userResponse).build();
  }
}
