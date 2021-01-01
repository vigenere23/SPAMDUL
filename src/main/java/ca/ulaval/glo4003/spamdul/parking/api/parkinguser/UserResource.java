package ca.ulaval.glo4003.spamdul.parking.api.parkinguser;

import ca.ulaval.glo4003.spamdul.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.api.parkinguser.dto.UserCreationResponse;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserCreationDto;
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
  public Response createNewUser(UserCreationRequest userCreationRequest) {
    UserCreationDto userCreationDto = userAssembler.fromRequest(userCreationRequest);
    UserId newUserId = userUseCase.createUser(userCreationDto);

    UserCreationResponse userCreationResponse = new UserCreationResponse();
    userCreationResponse.userId = newUserId.toString();

    return Response.status(Status.CREATED).entity(userCreationResponse).build();
  }
}
