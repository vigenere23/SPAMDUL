package ca.ulaval.glo4003.spamdul.context.revenue;

import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.transactions.InMemoryTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.RevenueResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.usecases.banking.BankingService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class RevenueContext {

  private final RevenueResourceImpl revenueResource;
  private TransactionRepository transactionRepository;
  private final TransactionService transactionService;


  public RevenueContext() {
    transactionRepository = new InMemoryTransactionRepository();
    TransactionFactory transactionFactory = new TransactionFactory();
    transactionService = new TransactionService(transactionRepository, transactionFactory);
    RevenueAssembler revenueAssembler = new RevenueAssembler();
    revenueResource = new RevenueResourceImpl(transactionService, revenueAssembler);
  }

  public RevenueResourceImpl getRevenueResource() {
    return revenueResource;
  }

  public TransactionRepository getTransactionRepository() {
    return transactionRepository;
  }

  public TransactionService getTransactionService() {
    return transactionService;
  }
}
