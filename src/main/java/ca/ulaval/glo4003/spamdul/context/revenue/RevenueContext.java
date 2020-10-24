package ca.ulaval.glo4003.spamdul.context.revenue;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.transactions.InMemoryTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.RevenueResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class RevenueContext {

  private final RevenueResourceImpl revenueResource;
  private final TransactionRepository transactionRepository;
  private final TransactionPopulator transactionPopulator;

  public RevenueContext(boolean populateData) {
    transactionRepository = new InMemoryTransactionRepository();
    TransactionFactory transactionFactory = new TransactionFactory();
    Calendar calendar = new HardCodedCalendar();
    TransactionQueryAssembler transactionQueryAssembler = new TransactionQueryAssembler(calendar);
    TransactionService transactionService = new TransactionService(transactionRepository, transactionFactory);
    RevenueAssembler revenueAssembler = new RevenueAssembler();
    transactionPopulator = new TransactionPopulator(transactionFactory, transactionRepository);
    revenueResource = new RevenueResourceImpl(transactionService, transactionQueryAssembler, revenueAssembler);

    if (populateData) {
      this.populateData();
    }
  }

  public RevenueResourceImpl getRevenueResource() {
    return revenueResource;
  }

  public TransactionRepository getTransactionRepository() {
    return transactionRepository;
  }

  private void populateData() {
    transactionPopulator.populate(60);
  }
}
