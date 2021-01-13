package ca.ulaval.glo4003.spamdul.finance.context.revenue;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.finance.RevenueAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.finance.TransactionQueryAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.FinanceAccessValidator;
import ca.ulaval.glo4003.spamdul.finance.api.revenue.RevenueResource;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.AccessRightTransactionService;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.transactions.InMemoryCampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.infrastructure.persistence.transactions.InMemoryTransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.usecases.revenue.RevenueUseCase;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.infrastructure.calendar.HardCodedCalendar;

public class RevenueContext implements ResourceContext {

  private final TransactionFactory transactionFactory;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CarbonCreditsTransactionService carbonCreditsTransactionService;
  private final CampusAccessTransactionService campusAccessTransactionService;
  private final InfractionTransactionService infractionTransactionService;
  private final InitiativeTransactionService initiativeTransactionService;
  private final PassTransactionService passTransactionService;
  private final AccessRightTransactionService accessRightTransactionService;

  private final RevenueResource revenueResource;

  public RevenueContext(AuthenticationRepository authenticationRepository,
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
    accessRightTransactionService = new AccessRightTransactionService(mainBankAccount,
                                                                      sustainabilityBankAccount,
                                                                      new InMemoryCampusAccessTransactionRepository());

    AccessLevelValidator accessLevelValidator = new FinanceAccessValidator(authenticationRepository);
    RevenueUseCase revenueUseCase = new RevenueUseCase(accessLevelValidator,
                                                       campusAccessTransactionService,
                                                       infractionTransactionService,
                                                       passTransactionService,
                                                       carbonCreditsTransactionService);

    Calendar calendar = new HardCodedCalendar();
    TransactionQueryAssembler transactionQueryAssembler = new TransactionQueryAssembler(calendar);
    RevenueAssembler revenueAssembler = new RevenueAssembler();
    revenueResource = new RevenueResource(revenueUseCase,
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

  public AccessRightTransactionService getAccessRightTransactionService() {
    return accessRightTransactionService;
  }

  @Override public void registerResources(InstanceMap resources) {
    resources.add(revenueResource);
  }
}
