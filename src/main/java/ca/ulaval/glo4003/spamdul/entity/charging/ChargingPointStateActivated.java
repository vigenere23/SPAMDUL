package ca.ulaval.glo4003.spamdul.entity.charging;

import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointAlreadyActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.shared.counter.MillisecondsCounter;

public class ChargingPointStateActivated implements ChargingPointState {

  private final ChargingPoint chargingPoint;
  private final MillisecondsCounter counter;
  private final long millisecondsUsed;

  public ChargingPointStateActivated(ChargingPoint chargingPoint, MillisecondsCounter counter, long millisecondsUsed) {
    this.chargingPoint = chargingPoint;
    this.counter = counter;
    this.millisecondsUsed = millisecondsUsed;
  }

  @Override public void activate() {
    throw new ChargingPointAlreadyActivatedException();
  }

  @Override public void connect() {
    counter.start(millisecondsUsed);
    chargingPoint.setState(new ChargingPointStateCharging(chargingPoint, counter));
  }

  @Override public void disconnect() {
    throw new ChargingPointNotChargingException();
  }

  @Override public long deactivate() {
    chargingPoint.setState(new ChargingPointStateIdle(chargingPoint));
    return millisecondsUsed;
  }

  @Override public String toString() {
    return "activated";
  }
}
