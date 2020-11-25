package ca.ulaval.glo4003.spamdul.context;

import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.InMemoryCampusAccessRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;

public class GlobalContext {

  private final PassRepository passRepository;
  private final TransactionFactory transactionFactory;
  private final AccessTokenCookieAssembler cookieAssembler;

  public GlobalContext() {
    passRepository = new InMemoryCampusAccessRepository();
    transactionFactory = new TransactionFactory();
    cookieAssembler = new AccessTokenCookieAssembler();
  }

  public PassRepository getPassRepository() {
    return passRepository;
  }

  public TransactionFactory getTransactionFactory() {
    return transactionFactory;
  }

  public AccessTokenCookieAssembler getCookieAssembler() {
    return cookieAssembler;
  }
}
