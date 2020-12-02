package ca.ulaval.glo4003.spamdul.context.finance;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.FinanceAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.infrastructure.calendar.HardCodedCalendar;
import ca.ulaval.glo4003.spamdul.infrastructure.db.finance.InMemoryCampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.finance.InMemoryTransactionRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.RevenueResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.RevenueResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance.FinanceExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.usecases.finance.RevenueService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class FinanceContext implements ResourceContext {

  private final TransactionFactory transactionFactory;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CarbonCreditsTransactionService carbonCreditsTransactionService;
  private final CampusAccessTransactionService campusAccessTransactionService;
  private final InfractionTransactionService infractionTransactionService;
  private final InitiativeTransactionService initiativeTransactionService;
  private final PassTransactionService passTransactionService;

  private final RevenueResource revenueResource;

  public FinanceContext(AuthenticationRepository authenticationRepository,
                        AccessTokenCookieAssembler cookieAssembler) {
    transactionFactory = new TransactionFactory();

    MainBankAccount mainBankAccount = new MainBankAccount(transactionFactory,
                                                          new InMemoryTransactionRepository(),
                                                          new InMemoryTransactionRepository());
    sustainabilityBankAccount = new SustainabilityBankAccount(transactionFactory,
                                                              new InMemoryTransactionRepository(),
                                                              new InMemoryTransactionRepository());

    carbonCreditsTransactionService = new CarbonCreditsTransactionService(transactionFactory,
                                                                          new InMemoryTransactionRepository());
    campusAccessTransactionService = new CampusAccessTransactionService(mainBankAccount,
                                                                        sustainabilityBankAccount,
                                                                        new InMemoryCampusAccessTransactionRepository());
    initiativeTransactionService = new InitiativeTransactionService(sustainabilityBankAccount);
    infractionTransactionService = new InfractionTransactionService(mainBankAccount, sustainabilityBankAccount);
    passTransactionService = new PassTransactionService(mainBankAccount, sustainabilityBankAccount);

    AccessLevelValidator accessLevelValidator = new FinanceAccessValidator(authenticationRepository);
    RevenueService revenueService = new RevenueService(accessLevelValidator,
                                                       campusAccessTransactionService,
                                                       infractionTransactionService,
                                                       passTransactionService,
                                                       carbonCreditsTransactionService);

    Calendar calendar = new HardCodedCalendar();
    TransactionQueryAssembler transactionQueryAssembler = new TransactionQueryAssembler(calendar);
    RevenueAssembler revenueAssembler = new RevenueAssembler();
    revenueResource = new RevenueResourceImpl(revenueService,
                                              transactionQueryAssembler,
                                              revenueAssembler,
                                              cookieAssembler);
  }

  public TransactionFactory getTransactionFactory() {
    return transactionFactory;
  }

  public SustainabilityBankAccount getSustainabilityBankAccount() {
    return sustainabilityBankAccount;
  }

  public CampusAccessTransactionService getCampusAccessBankAccount() {
    return campusAccessTransactionService;
  }

  public InfractionTransactionService getInfractionBankAccount() {
    return infractionTransactionService;
  }

  public CarbonCreditsTransactionService getCarbonCreditsBankAccount() {
    return carbonCreditsTransactionService;
  }

  public InitiativeTransactionService getInitiativesBankAccount() {
    return initiativeTransactionService;
  }

  public PassTransactionService getPassBankAccount() {
    return passTransactionService;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(revenueResource);
    resources.add(new FinanceExceptionMapper());
  }
}
