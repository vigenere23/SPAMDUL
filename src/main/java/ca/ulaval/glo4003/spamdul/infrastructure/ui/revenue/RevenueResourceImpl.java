package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.CarTypeTotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.TotalRevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;

public class RevenueResourceImpl implements RevenueResource {

  private TransactionService transactionService;
  private TransactionAssembler transactionAssembler;

  public RevenueResourceImpl(TransactionService transactionService, TransactionAssembler transactionAssembler) {
    this.transactionService = transactionService;
    this.transactionAssembler = transactionAssembler;
  }

  public TotalRevenueResponse getTotalRevenue() {
    Amount campusAccessTotalRevenue = transactionService.getCampusAccessTotalRevenue();
    Amount infractionsTotalRevenue = transactionService.getInfractionsTotalRevenue();
    Amount passTotalRevenue = transactionService.getPassTotalRevenue();
    Map<CarType, Amount> revenueByCarType = transactionService.getTotalCampusAccessRevenueByCarType();

    return transactionAssembler.toResponse(campusAccessTotalRevenue, passTotalRevenue, infractionsTotalRevenue, revenueByCarType);
  }

  public RevenueResponse getCampusAccessTotalRevenue() {
    Amount amount = transactionService.getCampusAccessTotalRevenue();

    return transactionAssembler.toResponse(amount);
  }

  public CarTypeTotalRevenueResponse getCarTypeTotalRevenue() {
    Map<CarType, Amount> revenueByCarType = transactionService.getTotalCampusAccessRevenueByCarType();

    return transactionAssembler.toResponse(revenueByCarType);
  }

  public RevenueResponse getInfractionsTotalRevenue() {
    Amount amount = transactionService.getInfractionsTotalRevenue();

    return transactionAssembler.toResponse(amount);
  }

  public RevenueResponse getParkingPassTotalRevenue() {
    Amount amount = transactionService.getPassTotalRevenue();

    return transactionAssembler.toResponse(amount);
  }
}
