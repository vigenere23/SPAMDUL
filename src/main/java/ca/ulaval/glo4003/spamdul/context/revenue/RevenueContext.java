package ca.ulaval.glo4003.spamdul.context.revenue;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.TransactionAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.revenue.FinancialReportResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.revenue.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;

public class RevenueContext {

  private final FinancialReportResourceImpl revenueResource;
  private final TransactionPopulator transactionPopulator;

  public RevenueContext(BankRepository bankRepository,
                        AuthenticationRepository authenticationRepository,
                        AccessTokenCookieAssembler cookieAssembler,
                        boolean populateData) {
    TransactionFactory transactionFactory = new TransactionFactory();
    Calendar calendar = new HardCodedCalendar();
    TransactionQueryAssembler transactionQueryAssembler = new TransactionQueryAssembler(calendar);
    AccessLevelValidator accessLevelValidator = new TransactionAccessValidator(authenticationRepository);
    TransactionService transactionService = new TransactionService(bankRepository, accessLevelValidator);
    RevenueAssembler revenueAssembler = new RevenueAssembler();

    transactionPopulator = new TransactionPopulator(transactionFactory, bankRepository);
    revenueResource = new FinancialReportResourceImpl(transactionService,
                                                      transactionQueryAssembler,
                                                      revenueAssembler,
                                                      cookieAssembler);

    if (populateData) {
      this.populateData();
    }
  }

  public FinancialReportResourceImpl getRevenueResource() {
    return revenueResource;
  }

  private void populateData() {
    transactionPopulator.populate(60);
  }
}
