package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/usage-report")
public interface UsageReportResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  UsageReportDto getUsageReport(@QueryParam("startDate") String startDate,
                                @QueryParam("endDate") String endDate,
                                @QueryParam("parkingZone") String parkingZone);

  @GET
  @Path("/summary")
  @Produces(MediaType.APPLICATION_JSON)
  UsageReportSummaryDto getUsageReportSummary();

  @GET
  @Path("/month")
  @Produces(MediaType.APPLICATION_JSON)
  UsageReportDto getUsageReportMonth();
}
