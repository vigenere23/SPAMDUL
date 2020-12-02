package ca.ulaval.glo4003.spamdul.infrastructure.ui.finance;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.CarbonBoughtResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.TotalRevenueResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

@Path("/financial-report")
public interface RevenueResource {

  @GET
  @Path("revenue")
  @Produces(MediaType.APPLICATION_JSON)
  TotalRevenueResponse getTotalRevenue(@QueryParam("startDate") String startDate,
                                       @QueryParam("endDate") String endDate,
                                       @CookieParam("accessToken") Cookie accessToken);

  @GET
  @Path("revenue/campus-access")
  @Produces(MediaType.APPLICATION_JSON)
  CarTypeTotalRevenueResponse getCarTypeTotalRevenue(@QueryParam("startDate") String startDate,
                                                     @QueryParam("endDate") String endDate,
                                                     @CookieParam("accessToken") Cookie accessToken);

  @GET
  @Path("revenue/infractions")
  @Produces(MediaType.APPLICATION_JSON)
  RevenueResponse getInfractionsTotalRevenue(@QueryParam("startDate") String startDate,
                                             @QueryParam("endDate") String endDate,
                                             @CookieParam("accessToken") Cookie accessToken);

  @GET
  @Path("revenue/parking-pass")
  @Produces(MediaType.APPLICATION_JSON)
  RevenueResponse getParkingPassTotalRevenue(@QueryParam("startDate") String startDate,
                                             @QueryParam("endDate") String endDate,
                                             @CookieParam("accessToken") Cookie accessToken);

  @GET
  @Path("spending/carbon-credits")
  @Produces(MediaType.APPLICATION_JSON)
  CarbonBoughtResponse getTotalBoughtCarbonCredit(@CookieParam("accessToken") Cookie accessToken);
}
