package ca.ulaval.glo4003.spamdul.api.finance;

import ca.ulaval.glo4003.spamdul.api.finance.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.api.finance.dto.CarbonBoughtResponse;
import ca.ulaval.glo4003.spamdul.api.finance.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.api.finance.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.finance.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.finance.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.usecases.finance.RevenueUseCase;
import ca.ulaval.glo4003.spamdul.usecases.finance.dto.TransactionQueryDto;
import java.util.Map;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

@Path("/financial-report")
public class RevenueResource {

  private final RevenueUseCase revenueUseCase;
  private final TransactionQueryAssembler transactionQueryAssembler;
  private final AccessTokenCookieAssembler cookieAssembler;
  private final RevenueAssembler revenueAssembler;

  public RevenueResource(RevenueUseCase revenueUseCase,
                         TransactionQueryAssembler transactionQueryAssembler,
                         RevenueAssembler revenueAssembler,
                         AccessTokenCookieAssembler cookieAssembler) {
    this.revenueUseCase = revenueUseCase;
    this.revenueAssembler = revenueAssembler;
    this.transactionQueryAssembler = transactionQueryAssembler;
    this.cookieAssembler = cookieAssembler;
  }

  @GET
  @Path("revenue")
  @Produces(MediaType.APPLICATION_JSON)
  public TotalRevenueResponse getTotalRevenue(@QueryParam("startDate") String startDate,
                                              @QueryParam("endDate") String endDate,
                                              @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);

    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount infractionsTotalRevenue = revenueUseCase.getInfractionsTotalRevenue(transactionQueryDto,
                                                                               temporaryToken);
    Amount passTotalRevenue = revenueUseCase.getPassTotalRevenue(transactionQueryDto,
                                                                 temporaryToken);
    Map<CarType, Amount> revenueByCarType = revenueUseCase.getCampusAccessTotalRevenueByCarType(transactionQueryDto,
                                                                                                temporaryToken);

    return revenueAssembler.toResponse(passTotalRevenue, infractionsTotalRevenue, revenueByCarType);
  }

  @GET
  @Path("revenue/campus-access")
  @Produces(MediaType.APPLICATION_JSON)
  public CarTypeTotalRevenueResponse getCarTypeTotalRevenue(@QueryParam("startDate") String startDate,
                                                            @QueryParam("endDate") String endDate,
                                                            @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);

    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Map<CarType, Amount> revenueByCarType = revenueUseCase.getCampusAccessTotalRevenueByCarType(transactionQueryDto,
                                                                                                temporaryToken);

    return revenueAssembler.toResponse(revenueByCarType);
  }

  @GET
  @Path("revenue/infractions")
  @Produces(MediaType.APPLICATION_JSON)
  public RevenueResponse getInfractionsTotalRevenue(@QueryParam("startDate") String startDate,
                                                    @QueryParam("endDate") String endDate,
                                                    @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);

    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = revenueUseCase.getInfractionsTotalRevenue(transactionQueryDto, temporaryToken);

    return revenueAssembler.toResponse(amount);
  }

  @GET
  @Path("revenue/parking-pass")
  @Produces(MediaType.APPLICATION_JSON)
  public RevenueResponse getParkingPassTotalRevenue(@QueryParam("startDate") String startDate,
                                                    @QueryParam("endDate") String endDate,
                                                    @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);

    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = revenueUseCase.getPassTotalRevenue(transactionQueryDto, temporaryToken);

    return revenueAssembler.toResponse(amount);
  }

  @GET
  @Path("spending/carbon-credits")
  @Produces(MediaType.APPLICATION_JSON)
  public CarbonBoughtResponse getTotalBoughtCarbonCredit(@CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);

    CarbonBoughtResponse response = new CarbonBoughtResponse();
    response.total = revenueUseCase.getAllBoughtCarbonCredit(temporaryToken).asDouble();

    return response;
  }
}
