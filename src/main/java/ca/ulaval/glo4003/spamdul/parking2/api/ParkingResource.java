package ca.ulaval.glo4003.spamdul.parking2.api;

import ca.ulaval.glo4003.spamdul.parking2.api.assemblers.UserCreationAssembler;
import ca.ulaval.glo4003.spamdul.parking2.api.dtos.UserCreationRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("parking")
public class ParkingResource {

  private final UserCreationAssembler userCreationAssembler;

  public ParkingResource(UserCreationAssembler userCreationAssembler) {
    this.userCreationAssembler = userCreationAssembler;
  }

  @Path("user")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void createUser(UserCreationRequest request) {
    
  }

  @Path("user/{id}/car")
  @POST
  public void addCar() {

  }

  @Path("access")
  @POST
  public void access() {

  }
}
