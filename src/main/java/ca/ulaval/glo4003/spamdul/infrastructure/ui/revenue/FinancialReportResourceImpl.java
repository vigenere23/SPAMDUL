package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarbonBoughtResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.Map;
import javax.ws.rs.core.Cookie;

public class FinancialReportResourceImpl implements FinancialReportResource {

  private final TransactionService transactionService;
  private final TransactionQueryAssembler transactionQueryAssembler;
  private AccessTokenCookieAssembler cookieAssembler;
  private final RevenueAssembler revenueAssembler;

  public FinancialReportResourceImpl(TransactionService transactionService,
                                     TransactionQueryAssembler transactionQueryAssembler,
                                     RevenueAssembler revenueAssembler,
                                     AccessTokenCookieAssembler cookieAssembler) {
    this.transactionService = transactionService;
    this.revenueAssembler = revenueAssembler;
    this.transactionQueryAssembler = transactionQueryAssembler;
    this.cookieAssembler = cookieAssembler;
  }

  @Override
  public TotalRevenueResponse getTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue(transactionQueryDto,
                                                                                   temporaryToken);
    Amount passTotalRevenue = transactionService.getPassTotalRevenue(transactionQueryDto,
                                                                     temporaryToken);
    Map<CarType, Amount> revenueByCarType = transactionService.getCampusAccessTotalRevenueByCarType(transactionQueryDto,
                                                                                                    temporaryToken);

    return revenueAssembler.toResponse(passTotalRevenue, infractionsTotalRevenue, revenueByCarType);
  }

  @Override
  public CarTypeTotalRevenueResponse getCarTypeTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Map<CarType, Amount> revenueByCarType = transactionService.getCampusAccessTotalRevenueByCarType(transactionQueryDto,
                                                                                                    temporaryToken);

    return revenueAssembler.toResponse(revenueByCarType);
  }

  @Override
  public RevenueResponse getInfractionsTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = transactionService.getInfractionsTotalRevenue(transactionQueryDto, temporaryToken);

    return revenueAssembler.toResponse(amount);
  }

  @Override
  public RevenueResponse getParkingPassTotalRevenue(String startDate, String endDate, Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
    TransactionQueryDto transactionQueryDto = transactionQueryAssembler.fromValues(startDate, endDate);
    Amount amount = transactionService.getPassTotalRevenue(transactionQueryDto, temporaryToken);

    return revenueAssembler.toResponse(amount);
  }

  public CarbonBoughtResponse getTotalBoughtCarbonCredit(Cookie accessToken) {
    TemporaryToken temporaryToken = TemporaryToken.valueOf(accessToken.getValue());
  @Override public CarbonBoughtResponse getTotalBoughtCarbonCredit() {
    CarbonBoughtResponse response = new CarbonBoughtResponse();
    response.total = transactionService.getAllBoughtCarbonCredit(temporaryToken).asDouble();

    return response;
  }
}
