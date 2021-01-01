package ca.ulaval.glo4003.spamdul.finance.context.carboncredits;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.api.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

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

    carbonCreditsResourceAdmin = new CarbonCreditsResourceAdmin(carbonCreditsUseCase);
  }

  @Override public void registerResources(InstanceMap resources) {
    super.registerResources(resources);
    resources.add(carbonCreditsResourceAdmin);
  }
}
