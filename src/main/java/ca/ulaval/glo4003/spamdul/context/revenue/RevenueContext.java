package ca.ulaval.glo4003.spamdul.context.revenue;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.RevenueResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class RevenueContext {

  private final TransactionService transactionService;
  private final RevenueAssembler revenueAssembler;
  private final RevenueResourceImpl revenueResource;

  public RevenueContext() {
    transactionService = new TransactionService();
    revenueAssembler = new RevenueAssembler();
    revenueResource = new RevenueResourceImpl(transactionService, revenueAssembler);
  }

  public RevenueResourceImpl getRevenueResource() {
    return revenueResource;
  }
}
