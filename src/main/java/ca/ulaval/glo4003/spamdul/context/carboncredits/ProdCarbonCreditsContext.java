package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;

public class ProdCarbonCreditsContext extends CarbonCreditsContext {

  public ProdCarbonCreditsContext(CarbonCreditsTransactionService carbonCreditsTransactionService,
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
  }
}
