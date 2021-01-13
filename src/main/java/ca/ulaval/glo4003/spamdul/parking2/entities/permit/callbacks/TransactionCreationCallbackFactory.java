package ca.ulaval.glo4003.spamdul.parking2.entities.permit.callbacks;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.AccessRightTransactionService;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;

public class TransactionCreationCallbackFactory {

  private final AccessRightTransactionService accessRightTransactionService;

  public TransactionCreationCallbackFactory(AccessRightTransactionService accessRightTransactionService) {
    this.accessRightTransactionService = accessRightTransactionService;
  }

  public TransactionCreationCallback create(CarType carType) {
    return new TransactionCreationCallback(accessRightTransactionService, carType);
  }
}
