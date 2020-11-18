package ca.ulaval.glo4003.spamdul.entity.charging_counter;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class ChargingCounter implements ChargingCounterState {

  private final Amount hourlyFee = Amount.valueOf(1);

  private final RechargULCard rechargULCard;
  private ChargingCounterState state = new ChargingCounterStateIdle(this);

  public ChargingCounter(RechargULCard rechargULCard) {
    verifyEnoughFunds(rechargULCard);
    this.rechargULCard = rechargULCard;
  }

  @Override public void start() {
    this.state.start();
  }

  @Override public void stop() {
    this.state.stop();
  }

  public void pay(long hours) {
    Amount total = hourlyFee.multiply(hours);
    rechargULCard.debit(total);
  }

  public void setState(ChargingCounterState state) {
    this.state = state;
  }

  private void verifyEnoughFunds(RechargULCard rechargULCard) {
    if (rechargULCard.hasUnpaidCharges()) {
      throw new NotEnoughCreditsException();
    }
  }
}
