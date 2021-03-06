package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointAlreadyChargingException;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointNotDisconnectedException;
import ca.ulaval.glo4003.spamdul.shared.entities.counter.MillisecondsCounter;

public class ChargingPointStateCharging implements ChargingPointState {

  private final ChargingPoint chargingPoint;
  private final MillisecondsCounter counter;

  public ChargingPointStateCharging(ChargingPoint chargingPoint, MillisecondsCounter counter) {
    this.chargingPoint = chargingPoint;
    this.counter = counter;
  }

  @Override public void activate() {
    throw new ChargingPointNotDisconnectedException();
  }

  @Override public void connect() {
    throw new ChargingPointAlreadyChargingException();
  }

  @Override public void disconnect() {
    long millisecondsUsed = counter.stop();
    chargingPoint.setState(new ChargingPointStateActivated(chargingPoint, counter, millisecondsUsed));
  }

  @Override public long deactivate() {
    throw new ChargingPointNotDisconnectedException();
  }

  @Override public String toString() {
    return "charging";
  }
}
