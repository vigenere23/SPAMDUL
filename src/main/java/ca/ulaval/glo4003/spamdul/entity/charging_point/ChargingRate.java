package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.concurrent.TimeUnit;

public class ChargingRate {

  private final Amount timeUnitFee;
  private final TimeUnit timeUnit;

  public ChargingRate(Amount timeUnitFee, TimeUnit timeUnit) {
    this.timeUnitFee = timeUnitFee;
    this.timeUnit = timeUnit;
  }

  public void pay(long milliseconds, RechargULCard card) {
    if (milliseconds <= 0) {
      return;
    }

    long duration = timeUnit.convert(milliseconds, TimeUnit.MILLISECONDS) + 1; // upper rounding
    Amount amount = timeUnitFee.multiply(duration);

    card.debit(amount);
  }
}
