package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;

public class ProdFundraisingContext extends FundraisingContext {

  public ProdFundraisingContext(InitiativeTransactionService initiativeTransactionService,
                                AuthenticationRepository authenticationRepository,
                                AccessTokenCookieAssembler cookieAssembler) {
    super(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  protected void populateData() {
    
  }
}
