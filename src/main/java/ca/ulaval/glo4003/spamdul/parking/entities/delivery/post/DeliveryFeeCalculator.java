package ca.ulaval.glo4003.spamdul.parking.entities.delivery.post;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.HashMap;
import java.util.Map;

public class DeliveryFeeCalculator {

  private final Map<DeliveryMode, Amount> deliveryFees = new HashMap<>();

  public DeliveryFeeCalculator() {
    setDeliveryFees();
  }

  public Amount calculateBy(DeliveryMode deliveryMode) {
    return deliveryFees.getOrDefault(deliveryMode, Amount.valueOf(0));
  }

  private void setDeliveryFees() {
    this.deliveryFees.put(DeliveryMode.POST, Amount.valueOf(5.0));
  }
}
