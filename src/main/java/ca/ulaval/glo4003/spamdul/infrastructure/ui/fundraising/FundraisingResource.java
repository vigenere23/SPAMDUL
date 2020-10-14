package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.FundraisingRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.FundraisingResponse;
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
  @Produces(MediaType.APPLICATION_JSON)
  List<FundraisingResponse> getInitiatives();

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  FundraisingResponse createInitiative(FundraisingRequest request);
}
