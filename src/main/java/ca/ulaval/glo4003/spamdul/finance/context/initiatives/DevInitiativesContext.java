package ca.ulaval.glo4003.spamdul.finance.context.initiatives;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;

public class DevInitiativesContext extends InitiativesContext {

  private final int NUMBER_OF_RECORDS = 30;

  public DevInitiativesContext(InitiativeTransactionService initiativeTransactionService,
                               AuthenticationRepository authenticationRepository,
                               AccessTokenCookieAssembler cookieAssembler) {
    super(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  @Override protected void populateData(Populator populator) {
    populator.populate(NUMBER_OF_RECORDS);
  }
}
