package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.CarbonCreditsAccessLevelValidator;
import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.finance.api.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.finance.infrastructure.carboncredits.ConsoleLogCarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.finance.usecases.carboncredits.CarbonCreditsUseCase;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.scheduling.EndOfMonthEventScheduler;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class CarbonCreditsContext implements ResourceContext {

  protected final CarbonCreditsUseCase carbonCreditsUseCase;
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

    carbonCreditsUseCase = new CarbonCreditsUseCase(endOfMonthEventScheduler,
                                                    carbonCreditsPurchaser,
                                                    initiativeRepository,
                                                    initiativeCreator,
                                                    accessLevelValidator,
                                                    carbonCreditsTransactionService,
                                                    sustainabilityBankAccount);

    carbonCreditsResource = new CarbonCreditsResource(carbonCreditsUseCase, cookieAssembler);
  }

  public EndOfMonthEventScheduler getEndOfMonthEventScheduler() {
    return endOfMonthEventScheduler;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(carbonCreditsResource);
  }
}
