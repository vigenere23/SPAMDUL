package ca.ulaval.glo4003.spamdul.context;

import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess.InMemoryCampusAccessRepository;

public class GlobalContext {

  private final PassRepository passRepository;
  private final TransactionFactory transactionFactory;

  public GlobalContext() {
    passRepository = new InMemoryCampusAccessRepository();
    transactionFactory = new TransactionFactory();
  }

  public PassRepository getPassRepository() {
    return passRepository;
  }

  public TransactionFactory getTransactionFactory() {
    return transactionFactory;
  }
}
