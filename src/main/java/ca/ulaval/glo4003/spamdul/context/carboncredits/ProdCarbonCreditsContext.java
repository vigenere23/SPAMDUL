package ca.ulaval.glo4003.spamdul.context.carboncredits;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;

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
