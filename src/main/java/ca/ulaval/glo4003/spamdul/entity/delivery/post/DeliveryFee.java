package ca.ulaval.glo4003.spamdul.entity.delivery.post;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class DeliveryFee {

  private final Amount fee;

  public DeliveryFee(Amount fee) {
    this.fee = fee;
  }

  public Amount getFee() {
    return fee;
  }

}
