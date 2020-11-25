package ca.ulaval.glo4003.spamdul.infrastructure.ui.finance;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.CarbonBoughtResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.finance.RevenueService;
import ca.ulaval.glo4003.spamdul.usecases.finance.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.Map;
import javax.ws.rs.core.Cookie;

public class RevenueResourceImpl implements RevenueResource {

  private final RevenueService revenueService;
  private final TransactionQueryAssembler transactionQueryAssembler;
  private final AccessTokenCookieAssembler cookieAssembler;
  private final RevenueAssembler revenueAssembler;

  public RevenueResourceImpl(RevenueService revenueService,
                             TransactionQueryAssembler transactionQueryAssembler,
                             RevenueAssembler revenueAssembler,
                             AccessTokenCookieAssembler cookieAssembler) {
    this.revenueService = revenueService;
    this.revenueAssembler = revenueAssembler;
    this.transactionQueryAssembler = transactionQueryAssembler;
    this.cookieAssembler = cookieAssembler;
  }

  @Override
  public TotalRevenueResponse getTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount infractionsTotalRevenue = revenueService.getInfractionsTotalRevenue(transactionQueryDto,
                                                                               temporaryToken);
    Amount passTotalRevenue = revenueService.getPassTotalRevenue(transactionQueryDto,
                                                                 temporaryToken);
    Map<CarType, Amount> revenueByCarType = revenueService.getCampusAccessTotalRevenueByCarType(transactionQueryDto,
                                                                                                temporaryToken);

    return revenueAssembler.toResponse(passTotalRevenue, infractionsTotalRevenue, revenueByCarType);
  }

  @Override
  public CarTypeTotalRevenueResponse getCarTypeTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Map<CarType, Amount> revenueByCarType = revenueService.getCampusAccessTotalRevenueByCarType(transactionQueryDto,
                                                                                                temporaryToken);

    return revenueAssembler.toResponse(revenueByCarType);
  }

  @Override
  public RevenueResponse getInfractionsTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = revenueService.getInfractionsTotalRevenue(transactionQueryDto, temporaryToken);

    return revenueAssembler.toResponse(amount);
  }

  @Override
  public RevenueResponse getParkingPassTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = revenueService.getPassTotalRevenue(transactionQueryDto, temporaryToken);

    return revenueAssembler.toResponse(amount);
  }

  @Override public CarbonBoughtResponse getTotalBoughtCarbonCredit(Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    CarbonBoughtResponse response = new CarbonBoughtResponse();
    response.total = revenueService.getAllBoughtCarbonCredit(temporaryToken).asDouble();

    return response;
  }
}
