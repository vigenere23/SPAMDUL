package ca.ulaval.glo4003.spamdul.parking2.entities.permit.callbacks;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidInfos;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObserver;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.AccessRightTransactionService;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;

public class TransactionCreationCallback implements InvoicePaidObserver {

  private final AccessRightTransactionService accessRightTransactionService;
  private final CarType carType;

  public TransactionCreationCallback(AccessRightTransactionService accessRightTransactionService,
                                     CarType carType) {
    this.accessRightTransactionService = accessRightTransactionService;
    this.carType = carType;
  }

  @Override public void handleInvoicePaid(InvoicePaidInfos invoicePaidInfos) {
    accessRightTransactionService.addRevenue(invoicePaidInfos.getTotal(), carType);
  }
}
