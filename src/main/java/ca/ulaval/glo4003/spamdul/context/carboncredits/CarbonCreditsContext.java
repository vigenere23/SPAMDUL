package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.carboncredits.ConsoleLogCarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.infrastructure.scheduling.EndOfMonthEventScheduler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CarbonCreditsContext {

  private final CarbonCreditsResource carbonCreditsResource;
  private EndOfMonthEventScheduler endOfMonthEventScheduler;

  public CarbonCreditsContext(BankRepository bankRepository) {
    Calendar calendar = new HardCodedCalendar();
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    CarbonCreditsPurchaser carbonCreditsPurchaser = new ConsoleLogCarbonCreditsPurchaser();
    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(
        scheduledExecutorService,
        calendar
    );
    TransactionFactory transactionFactory = new TransactionFactory();
    CarbonCreditsService carbonCreditsService = new CarbonCreditsService(endOfMonthEventScheduler,
                                                                         bankRepository,
                                                                         transactionFactory);
                    scheduledExecutorService,
                    calendar
            );
    CarbonCreditsService carbonCreditsService = new CarbonCreditsService(
            endOfMonthEventScheduler,
            carbonCreditsPurchaser);
    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService);
  }

  public CarbonCreditsResource getCarbonCreditsResource() {
    return carbonCreditsResource;
  }

  public EndOfMonthEventScheduler getEndOfMonthEventScheduler() {
    return endOfMonthEventScheduler;
  }
}
