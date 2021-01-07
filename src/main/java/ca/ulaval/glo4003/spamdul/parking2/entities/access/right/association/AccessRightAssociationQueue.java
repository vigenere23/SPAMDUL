package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoicePaidObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessRightAssociationQueue implements InvoicePaidObserver {

  private final AccessRightAssociator accessRightAssociator;
  private final Map<InvoiceId, List<AccessRightAssociationAction>> callbacksByInvoiceId = new HashMap<>();

  public AccessRightAssociationQueue(AccessRightAssociator accessRightAssociator) {
    this.accessRightAssociator = accessRightAssociator;
  }

  public void add(InvoiceId invoiceId, AccessRightAssociationAction callback) {
    List<AccessRightAssociationAction> callbacks = callbacksByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
    callbacks.add(callback);
    callbacksByInvoiceId.put(invoiceId, callbacks);
  }

  @Override
  public void handleInvoicePaid(InvoiceId invoiceId) {
    List<AccessRightAssociationAction> callbacks = callbacksByInvoiceId.get(invoiceId);

    if (callbacks != null) {
      callbacks.forEach(callback -> callback.trigger(accessRightAssociator));
      callbacksByInvoiceId.remove(invoiceId);
    }
  }
}
