package ca.ulaval.glo4003.spamdul.ui.campusaccess;

import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.CampusAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.AccessingCampusDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto.CampusAccessDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.CampusAccessService;
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
  private final CampusAccessService campusAccessService;

  public CampusAccessResource(CampusAccessAssembler campusAccessAssembler,
                              CampusAccessService campusAccessService) {
    this.campusAccessAssembler = campusAccessAssembler;
    this.campusAccessService = campusAccessService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCampusAccess(CampusAccessRequest campusAccessRequest) {
    CampusAccessDto campusAccessDto = campusAccessAssembler.fromRequest(campusAccessRequest);
    CampusAccessDto campusAccess = campusAccessService.createCampusAccess(campusAccessDto);

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
    boolean isAccessGranted = campusAccessService.grantAccessToCampus(accessingCampusDto);

    return campusAccessAssembler.toResponse(isAccessGranted);
  }
}
