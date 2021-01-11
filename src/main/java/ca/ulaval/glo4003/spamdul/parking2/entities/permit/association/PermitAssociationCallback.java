package ca.ulaval.glo4003.spamdul.parking2.entities.permit.association;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidInfos;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObserver;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public class PermitAssociationCallback implements InvoicePaidObserver {

  private final PermitAssociator permitAssociator;
  private final AccountId accountId;
  private final Permit permit;

  public PermitAssociationCallback(PermitAssociator permitAssociator,
                                   AccountId accountId,
                                   Permit permit) {
    this.permitAssociator = permitAssociator;
    this.accountId = accountId;
    this.permit = permit;
  }

  @Override public void handleInvoicePaid(InvoicePaidInfos invoicePaidInfos) {
    permitAssociator.associatePermit(accountId, permit);
  }
}
