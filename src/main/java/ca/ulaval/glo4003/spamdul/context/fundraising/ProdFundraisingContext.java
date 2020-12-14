package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;

public class ProdFundraisingContext extends FundraisingContext {

  public ProdFundraisingContext(InitiativeTransactionService initiativeTransactionService,
                                AuthenticationRepository authenticationRepository,
                                AccessTokenCookieAssembler cookieAssembler) {
    super(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  @Override protected void populateData(Populator populator) {
  }
}
