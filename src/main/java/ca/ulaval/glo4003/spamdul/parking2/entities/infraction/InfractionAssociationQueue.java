package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoicePaidObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfractionAssociationQueue implements InvoicePaidObserver {

  private final InfractionAssociator infractionAssociator;
  private final Map<InvoiceId, List<InfractionAssociationAction>> actionsByInvoiceId = new HashMap<>();

  public InfractionAssociationQueue(InfractionAssociator infractionAssociator) {
    this.infractionAssociator = infractionAssociator;
  }

  public void add(InvoiceId invoiceId, InfractionAssociationAction action) {
    List<InfractionAssociationAction> actions = actionsByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
    actions.add(action);
    actionsByInvoiceId.put(invoiceId, actions);
  }

  @Override
  public void handleInvoicePaid(InvoiceId invoiceId) {
    List<InfractionAssociationAction> actions = actionsByInvoiceId.get(invoiceId);

    if (actions != null) {
      actions.forEach(action -> action.trigger(infractionAssociator));
      actionsByInvoiceId.remove(invoiceId);
    }
  }
}
