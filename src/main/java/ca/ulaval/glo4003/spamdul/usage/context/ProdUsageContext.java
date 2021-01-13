package ca.ulaval.glo4003.spamdul.usage.context;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.shared.context.RecordPopulator;

public class ProdUsageContext extends UsageContext {

  public ProdUsageContext(AuthenticationRepository authenticationRepository,
                          AccessTokenCookieAssembler cookieAssembler) {
    super(authenticationRepository, cookieAssembler);
  }

  @Override
  protected void populateData(RecordPopulator recordPopulator) {
  }
}
