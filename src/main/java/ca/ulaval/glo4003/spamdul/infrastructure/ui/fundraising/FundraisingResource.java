package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/fundraising")
public interface FundraisingResource {

  @GET
  @Path("/initiatives")
  @Produces(MediaType.APPLICATION_JSON)
  List<InitiativeResponse> getInitiatives();

  @POST
  @Path("/initiatives")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  InitiativeResponse createInitiative(InitiativeRequest request);
}
