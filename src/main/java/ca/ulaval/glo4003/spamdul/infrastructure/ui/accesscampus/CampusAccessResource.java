package ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.CampusAccessRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.CampusAccessResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public interface CampusAccessResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  CampusAccessResponse createCampusAccess(CampusAccessRequest campusAccessRequest);
}
