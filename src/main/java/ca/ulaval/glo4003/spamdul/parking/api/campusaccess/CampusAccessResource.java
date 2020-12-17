package ca.ulaval.glo4003.spamdul.parking.api.campusaccess;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.CampusAccessUseCase;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.dto.CampusAccessDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/campus-access")
public class CampusAccessResource {

  private final CampusAccessAssembler campusAccessAssembler;
  private final CampusAccessUseCase campusAccessUseCase;

  public CampusAccessResource(CampusAccessAssembler campusAccessAssembler,
                              CampusAccessUseCase campusAccessUseCase) {
    this.campusAccessAssembler = campusAccessAssembler;
    this.campusAccessUseCase = campusAccessUseCase;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCampusAccess(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);
    CampusAccessDto campusAccess = campusAccessUseCase.createCampusAccess(campusAccessDto);

    return Response.status(Status.CREATED)
                   .entity(campusAccessAssembler.toResponse(campusAccess))
                   .build();
  }

  @POST
  @Path("access")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public AccessingCampusResponse canAccessCampus(AccessingCampusRequest accessingCampusRequest) {
    AccessingCampusDto accessingCampusDto = campusAccessAssembler.fromRequest(accessingCampusRequest);
    boolean isAccessGranted = campusAccessUseCase.grantAccessToCampus(accessingCampusDto);

    return campusAccessAssembler.toResponse(isAccessGranted);
  }
}
