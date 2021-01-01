package ca.ulaval.glo4003.spamdul.usage.context;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;

public class DevUsageContext extends UsageContext {

  private static final int NUMBER_OF_ACCESS_LOGS = 100;

  public DevUsageContext(AuthenticationRepository authenticationRepository,
                         AccessTokenCookieAssembler cookieAssembler) {
    super(authenticationRepository, cookieAssembler);
  }

  @Override
  protected void populateData(Populator populator) {
    populator.populate(NUMBER_OF_ACCESS_LOGS);
  }
}
