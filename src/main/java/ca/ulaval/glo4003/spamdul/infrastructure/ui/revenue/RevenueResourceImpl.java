package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;

public class RevenueResourceImpl implements RevenueResource {

  private TransactionService transactionService;
  private RevenueAssembler revenueAssembler;

  public RevenueResourceImpl(TransactionService transactionService, RevenueAssembler revenueAssembler) {
    this.transactionService = transactionService;
    this.revenueAssembler = revenueAssembler;
  }

  public TotalRevenueResponse getTotalRevenue() {
    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue();
    Amount passTotalRevenue = transactionService.getPassTotalRevenue();
    Map<CarType, Amount> revenueByCarType = transactionService.getTotalCampusAccessRevenueByCarType();

    return revenueAssembler.toResponse(passTotalRevenue, infractionsTotalRevenue, revenueByCarType);
  }

  public CarTypeTotalRevenueResponse getCarTypeTotalRevenue() {
    Map<CarType, Amount> revenueByCarType = transactionService.getTotalCampusAccessRevenueByCarType();

    return revenueAssembler.toResponse(revenueByCarType);
  }

  public RevenueResponse getInfractionsTotalRevenue() {
    Amount amount = transactionService.getInfractionsTotalRevenue();

    return revenueAssembler.toResponse(amount);
  }

  public RevenueResponse getParkingPassTotalRevenue() {
    Amount amount = transactionService.getPassTotalRevenue();

    return revenueAssembler.toResponse(amount);
  }
}
