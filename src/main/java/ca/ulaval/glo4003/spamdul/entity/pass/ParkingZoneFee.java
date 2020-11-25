package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class ParkingZoneFee {

  private final Amount fee;

  public ParkingZoneFee(Amount fee) {
    this.fee = fee;
  }

  public Amount getFee() {
    return fee;
  }

}
