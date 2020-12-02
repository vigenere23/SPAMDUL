package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.CarbonCreditsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.carboncredits.ConsoleLogCarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.infrastructure.scheduling.EndOfMonthEventScheduler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceImpl;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class CarbonCreditsContext implements ResourceContext {

  protected final CarbonCreditsService carbonCreditsService;
  private final CarbonCreditsResource carbonCreditsResource;
  private final EndOfMonthEventScheduler endOfMonthEventScheduler;


  public CarbonCreditsContext(CarbonCreditsTransactionService carbonCreditsTransactionService,
                              SustainabilityBankAccount sustainabilityBankAccount,
                              InitiativeRepository initiativeRepository,
                              InitiativeCreator initiativeCreator,
                              AuthenticationRepository authenticationRepository,
                              AccessTokenCookieAssembler cookieAssembler) {
    Calendar calendar = new HardCodedCalendar();
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    CarbonCreditsPurchaser carbonCreditsPurchaser = new ConsoleLogCarbonCreditsPurchaser();

    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(
        scheduledExecutorService,
        calendar
    );

    AccessLevelValidator accessLevelValidator = new CarbonCreditsAccessLevelValidator(authenticationRepository);

    carbonCreditsService = new CarbonCreditsService(endOfMonthEventScheduler,
                                                    carbonCreditsPurchaser,
                                                    initiativeRepository,
                                                    initiativeCreator,
                                                    accessLevelValidator,
                                                    carbonCreditsTransactionService,
                                                    sustainabilityBankAccount);

    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService, cookieAssembler);
  }

  public EndOfMonthEventScheduler getEndOfMonthEventScheduler() {
    return endOfMonthEventScheduler;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(carbonCreditsResource);
  }
}
