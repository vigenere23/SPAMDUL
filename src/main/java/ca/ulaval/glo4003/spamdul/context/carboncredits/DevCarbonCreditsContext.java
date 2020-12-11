package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class DevCarbonCreditsContext extends CarbonCreditsContext {

  private final CarbonCreditsResourceAdmin carbonCreditsResourceAdmin;

  public DevCarbonCreditsContext(CarbonCreditsTransactionService carbonCreditsTransactionService,
                                 SustainabilityBankAccount sustainabilityBankAccount,
                                 InitiativeRepository initiativeRepository,
                                 InitiativeCreator initiativeCreator,
                                 AuthenticationRepository authenticationRepository,
                                 AccessTokenCookieAssembler cookieAssembler) {
    super(carbonCreditsTransactionService,
          sustainabilityBankAccount,
          initiativeRepository,
          initiativeCreator,
          authenticationRepository,
          cookieAssembler);

    carbonCreditsResourceAdmin = new CarbonCreditsResourceAdmin(carbonCreditsService);
  }

  @Override public void registerResources(InstanceMap resources) {
    super.registerResources(resources);
    resources.add(carbonCreditsResourceAdmin);
  }
}
