package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidInfos;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.paid_event.InvoicePaidObserver;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;

public class AccessRightAssociationCallback implements InvoicePaidObserver {

  private final AccessRightAssociator accessRightAssociator;
  private final LicensePlate licensePlate;
  private final AccessRight accessRight;

  public AccessRightAssociationCallback(AccessRightAssociator accessRightAssociator,
                                        LicensePlate licensePlate,
                                        AccessRight accessRight) {
    this.accessRightAssociator = accessRightAssociator;
    this.licensePlate = licensePlate;
    this.accessRight = accessRight;
  }

  @Override public void handleInvoicePaid(InvoicePaidInfos invoicePaidInfos) {
    accessRightAssociator.associateAccessRight(licensePlate, accessRight);
  }
}
