package ca.ulaval.glo4003.spamdul.context.usagereport;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;

public class ProdUsageReportContext extends UsageReportContext {

  public ProdUsageReportContext(AuthenticationRepository authenticationRepository,
                                AccessTokenCookieAssembler cookieAssembler) {
    super(authenticationRepository, cookieAssembler);
  }

  @Override
  protected void populateData(Populator populator) {
  }
}
