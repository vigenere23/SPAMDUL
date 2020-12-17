package ca.ulaval.glo4003.spamdul.parking.api.pass;

import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.usecases.pass.ParkingPassUseCase;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/pass")
public class ParkingPassResource {

  private final PassAssembler passAssembler;
  private final ParkingPassUseCase parkingPassUseCase;

  public ParkingPassResource(ParkingPassUseCase parkingPassUseCase, PassAssembler passAssembler) {
    this.parkingPassUseCase = parkingPassUseCase;
    this.passAssembler = passAssembler;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response sellPass(PassCreationRequest passCreationRequest) {
    this.parkingPassUseCase.createPass(passAssembler.fromRequest(passCreationRequest));

    return Response.status(Status.CREATED)
                   .build();
  }
}
