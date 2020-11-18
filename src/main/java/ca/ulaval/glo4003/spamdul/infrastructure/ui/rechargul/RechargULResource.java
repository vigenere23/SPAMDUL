package ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCreditsRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/rechargul")
public interface RechargULResource {

  @Path("/{id}")
  @GET
  Response getCard(@PathParam("id") String rechargULCardIdString);

  @Path("/{id}/credits")
  @POST
  Response addCredits(@PathParam("id") String rechargULCardIdString, RechargULCreditsRequest request);
}
