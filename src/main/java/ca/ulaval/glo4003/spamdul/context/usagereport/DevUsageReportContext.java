package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;

public class DevUsageReportContext extends UsageReportContext {

  private static final int NUMBER_OF_ACCESS_LOGS = 100;

  public DevUsageReportContext(AuthenticationRepository authenticationRepository,
                               AccessTokenCookieAssembler cookieAssembler) {
    super(authenticationRepository, cookieAssembler);
  }

  @Override
  protected void populateData(Populator populator) {
    populator.populate(NUMBER_OF_ACCESS_LOGS);
  }
}
