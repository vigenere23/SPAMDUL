package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.CarbonCreditsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.carboncredits.ConsoleLogCarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.infrastructure.scheduling.EndOfMonthEventScheduler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceAdminImpl;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceImpl;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.NullCarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CarbonCreditsContext {

  private final CarbonCreditsResource carbonCreditsResource;
  private CarbonCreditsResourceAdmin carbonCreditsResourceAdmin = new NullCarbonCreditsResourceAdmin();
  private final EndOfMonthEventScheduler endOfMonthEventScheduler;

  public CarbonCreditsContext(BankRepository bankRepository,
                              TransactionFactory transactionFactory,
                              InitiativeFactory initiativeFactory,
                              InitiativeRepository initiativeRepository,
                              AuthenticationRepository authenticationRepository,
                              AccessTokenCookieAssembler cookieAssembler,
                              boolean asAdmin) {
    Calendar calendar = new HardCodedCalendar();
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    CarbonCreditsPurchaser carbonCreditsPurchaser = new ConsoleLogCarbonCreditsPurchaser();

    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(
        scheduledExecutorService,
        calendar
    );

    AccessLevelValidator accessLevelValidator = new CarbonCreditsAccessLevelValidator(authenticationRepository);

    CarbonCreditsService carbonCreditsService = new CarbonCreditsService(endOfMonthEventScheduler,
                                                                         bankRepository,
                                                                         transactionFactory,
                                                                         carbonCreditsPurchaser,
                                                                         initiativeFactory,
                                                                         initiativeRepository,
                                                                         accessLevelValidator);

    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService, cookieAssembler);

    if (asAdmin) {
      carbonCreditsResourceAdmin = new CarbonCreditsResourceAdminImpl(carbonCreditsService);
    }
  }

  public CarbonCreditsResource getCarbonCreditsResource() {
    return carbonCreditsResource;
  }

  public CarbonCreditsResourceAdmin getCarbonCreditsResourceAdmin() {
    return carbonCreditsResourceAdmin;
  }

  public EndOfMonthEventScheduler getEndOfMonthEventScheduler() {
    return endOfMonthEventScheduler;
  }
}
