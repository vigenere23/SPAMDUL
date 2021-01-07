package ca.ulaval.glo4003.spamdul.parking2.entities.permit.association;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoicePaidObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermitAssociationQueue implements InvoicePaidObserver {

  private final PermitAssociator permitAssociator;
  private final Map<InvoiceId, List<PermitAssociationAction>> actionsByInvoiceId = new HashMap<>();

  public PermitAssociationQueue(PermitAssociator permitAssociator) {
    this.permitAssociator = permitAssociator;
  }

  public void add(InvoiceId invoiceId, PermitAssociationAction action) {
    List<PermitAssociationAction> actions = actionsByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
    actions.add(action);
    actionsByInvoiceId.put(invoiceId, actions);
  }

  @Override
  public void handleInvoicePaid(InvoiceId invoiceId) {
    List<PermitAssociationAction> actions = actionsByInvoiceId.get(invoiceId);

    if (actions != null) {
      actions.forEach(action -> action.trigger(permitAssociator));
      actionsByInvoiceId.remove(invoiceId);
    }
  }
}
