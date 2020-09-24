package ca.ulaval.glo4003.spamdul.infrastructure.ui.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.parkingaccesslog.dto.ParkingAccessLog;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/parking-access")
public interface ParkingAccessLogResource {
  // TODO something to do here!

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  List<ParkingAccessLog> getParkingAccessLogLogs();

//  @DELETE
//  @Path("{id}")
//  void deleteCallLog(@PathParam("id") String id);
}
