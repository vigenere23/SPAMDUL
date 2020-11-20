package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativesResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/fundraising")
public interface FundraisingResource {

  @GET
  @Path("/initiatives")
  @Produces(MediaType.APPLICATION_JSON)
  InitiativesResponse getInitiatives(@CookieParam("accessToken") Cookie accessToken);

  @POST
  @Path("/initiatives")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response createInitiative(InitiativeRequest request, @CookieParam("accessToken") Cookie accessToken);
}
