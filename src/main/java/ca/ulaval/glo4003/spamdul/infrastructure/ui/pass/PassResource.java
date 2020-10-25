package ca.ulaval.glo4003.spamdul.infrastructure.ui.pass;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PassCreationRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pass")
public interface PassResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  Response sellPass(PassCreationRequest passCreationRequest);
}
