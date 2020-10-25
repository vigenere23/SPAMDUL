package ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.AccessingCampusResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.CampusAccessRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/campus-access")
public interface CampusAccessResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response createCampusAccess(CampusAccessRequest campusAccessRequest);

  @POST
  @Path("access")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  AccessingCampusResponse canAccessCampus(AccessingCampusRequest accessingCampusRequest);
}
