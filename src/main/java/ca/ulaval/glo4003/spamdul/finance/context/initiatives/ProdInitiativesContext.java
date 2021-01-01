package ca.ulaval.glo4003.spamdul.finance.context.initiatives;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;

public class ProdInitiativesContext extends InitiativesContext {

  public ProdInitiativesContext(InitiativeTransactionService initiativeTransactionService,
                                AuthenticationRepository authenticationRepository,
                                AccessTokenCookieAssembler cookieAssembler) {
    super(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  @Override protected void populateData(Populator populator) {
  }
}
