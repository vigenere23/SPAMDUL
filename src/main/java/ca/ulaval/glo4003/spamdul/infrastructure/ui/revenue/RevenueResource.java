package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarbonBoughtResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/financial-report")
public interface RevenueResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  TotalRevenueResponse getTotalRevenue(@QueryParam("startDate") String startDate,
                                       @QueryParam("endDate") String endDate);

  @GET
  @Path("revenue/campus-access")
  @Produces(MediaType.APPLICATION_JSON)
  CarTypeTotalRevenueResponse getCarTypeTotalRevenue(@QueryParam("startDate") String startDate,
                                                     @QueryParam("endDate") String endDate);

  @GET
  @Path("revenue/infractions")
  @Produces(MediaType.APPLICATION_JSON)
  RevenueResponse getInfractionsTotalRevenue(@QueryParam("startDate") String startDate,
                                             @QueryParam("endDate") String endDate);

  @GET
  @Path("revenue/parking-pass")
  @Produces(MediaType.APPLICATION_JSON)
  RevenueResponse getParkingPassTotalRevenue(@QueryParam("startDate") String startDate,
                                             @QueryParam("endDate") String endDate);

  @GET
  @Path("spending/carbon-credits")
  @Produces(MediaType.APPLICATION_JSON)
  CarbonBoughtResponse getTotalBoughtCarbonCredit();
}
