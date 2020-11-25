package ca.ulaval.glo4003.spamdul.entity.delivery.post;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.HashMap;
import java.util.Map;

public class DeliveryFeeCalculator {

  private final Map<DeliveryMode, DeliveryFee> deliveryFees = new HashMap<>();

  public DeliveryFeeCalculator() {
    setDeliveryFees();
  }

  public DeliveryFee calculateBy(DeliveryMode deliveryMode) {
    return deliveryFees.get(deliveryMode);
  }

  private void setDeliveryFees() {
    this.deliveryFees.put(DeliveryMode.POST, new DeliveryFee(Amount.valueOf(5.0)));
    this.deliveryFees.put(DeliveryMode.SSP_OFFICE, new DeliveryFee(Amount.valueOf(0.0)));
    this.deliveryFees.put(DeliveryMode.EMAIL, new DeliveryFee(Amount.valueOf(0.0)));
  }
}
