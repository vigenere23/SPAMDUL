package ca.ulaval.glo4003.spamdul.entity.delivery.post;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
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
