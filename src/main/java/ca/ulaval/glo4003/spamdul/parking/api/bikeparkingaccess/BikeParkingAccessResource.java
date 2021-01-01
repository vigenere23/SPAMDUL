package ca.ulaval.glo4003.spamdul.parking.api.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.assemblers.parking.bikeparking.BikeParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.api.bikeparkingaccess.dto.BikeParkingAccessResponse;
import ca.ulaval.glo4003.spamdul.parking.usecases.bikeparkingaccess.BikeParkingAccessUseCase;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/bike-parking/access")
public class BikeParkingAccessResource {

  private final BikeParkingAccessUseCase bikeParkingAccessUseCase;
  private final BikeParkingAccessAssembler bikeParkingAccessAssembler;


  public BikeParkingAccessResource(BikeParkingAccessUseCase bikeParkingAccessUseCase,
                                   BikeParkingAccessAssembler bikeParkingAccessAssembler) {
    this.bikeParkingAccessUseCase = bikeParkingAccessUseCase;
    this.bikeParkingAccessAssembler = bikeParkingAccessAssembler;
  }

  @POST
  @Path("/{bikeParkingAccessCode}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response accessBikeParking(@PathParam("bikeParkingAccessCode") String bikeParkingAccessCode) {
    BikeParkingPassCode accessCode = BikeParkingPassCode.valueOf(bikeParkingAccessCode);
    boolean canAccessParking = bikeParkingAccessUseCase.canAccessParking(accessCode);
    BikeParkingAccessResponse bikeParkingAccessResponse = bikeParkingAccessAssembler.toResponse(canAccessParking);

    return Response.status(Status.OK)
                   .entity(bikeParkingAccessResponse)
                   .build();
  }
}
