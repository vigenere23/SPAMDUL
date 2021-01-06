package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoicePaidObserver;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessRightAssociator implements InvoicePaidObserver {

  private final ParkingUserRepository parkingUserRepository;
  private final Map<InvoiceId, List<AccessRightBuyingCallback>> callbacksByInvoiceId = new HashMap<>();

  public AccessRightAssociator(ParkingUserRepository parkingUserRepository) {
    this.parkingUserRepository = parkingUserRepository;
  }

  public void addListener(InvoiceId invoiceId, AccessRightBuyingCallback callback) {
    List<AccessRightBuyingCallback> callbacks = callbacksByInvoiceId.getOrDefault(invoiceId, new ArrayList<>());
    callbacks.add(callback);
    callbacksByInvoiceId.put(invoiceId, callbacks);
  }

  @Override
  public void handleInvoicePaid(InvoiceId invoiceId) {
    List<AccessRightBuyingCallback> callbacks = callbacksByInvoiceId.get(invoiceId);

    if (callbacks != null) {
      callbacks.forEach(callback -> callback.trigger(parkingUserRepository));
      callbacksByInvoiceId.remove(invoiceId);
    }
  }
}
