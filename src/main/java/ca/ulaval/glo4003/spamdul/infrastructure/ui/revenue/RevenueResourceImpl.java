package ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.dto.RevenueResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class RevenueResourceImpl implements RevenueResource{

  private TransactionService transactionService;
  private TransactionAssembler transactionAssembler;

  public RevenueResourceImpl(TransactionService transactionService, TransactionAssembler transactionAssembler) {
    this.transactionService = transactionService;
    this.transactionAssembler = transactionAssembler;
  }

  public RevenueResponse getCampusAccessTotalRevenue() {
    Amount amount = transactionService.getCampusAccessTotalRevenue();

    return transactionAssembler.toResponse(amount);
  }

  public RevenueResponse getCampusAccessTotalRevenue(String carType) {
    return null;
  }

  public RevenueResponse getInfractionsTotalRevenue() {
    return null;
  }

  public RevenueResponse getParkingPassTotalRevenue() {
    return null;
  }
}
