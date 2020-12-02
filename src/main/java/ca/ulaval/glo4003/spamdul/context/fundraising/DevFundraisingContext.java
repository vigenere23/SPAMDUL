package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;

public class DevFundraisingContext extends FundraisingContext {

  private final int NUMBER_OF_RECORDS = 30;

  public DevFundraisingContext(InitiativeTransactionService initiativeTransactionService,
                               AuthenticationRepository authenticationRepository,
                               AccessTokenCookieAssembler cookieAssembler) {
    super(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  @Override protected void populateData() {
    new InitiativePopulator(initiativeRepository, initiativeFactory).populate(NUMBER_OF_RECORDS);
  }
}
