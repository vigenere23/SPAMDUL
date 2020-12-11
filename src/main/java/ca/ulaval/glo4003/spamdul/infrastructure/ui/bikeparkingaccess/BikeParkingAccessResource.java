package ca.ulaval.glo4003.spamdul.infrastructure.ui.bikeparkingaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.bikeparkingaccess.dto.BikeParkingAccessResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.bikeparking.BikeParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess.BikeParkingAccessService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/bike-parking/access")
public class BikeParkingAccessResource {

  private final BikeParkingAccessService bikeParkingAccessService;
  private final BikeParkingAccessAssembler bikeParkingAccessAssembler;


  public BikeParkingAccessResource(BikeParkingAccessService bikeParkingAccessService,
                                   BikeParkingAccessAssembler bikeParkingAccessAssembler) {
    this.bikeParkingAccessService = bikeParkingAccessService;
    this.bikeParkingAccessAssembler = bikeParkingAccessAssembler;
  }

  @POST
  @Path("/{bikeParkingAccessCode}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response accessBikeParking(@PathParam("bikeParkingAccessCode") String bikeParkingAccessCode) {
    BikeParkingPassCode accessCode = BikeParkingPassCode.valueOf(bikeParkingAccessCode);
    boolean canAccessParking = bikeParkingAccessService.canAccessParking(accessCode);
    BikeParkingAccessResponse bikeParkingAccessResponse = bikeParkingAccessAssembler.toResponse(canAccessParking);

    return Response.status(Status.OK)
                   .entity(bikeParkingAccessResponse)
                   .build();
  }
}
