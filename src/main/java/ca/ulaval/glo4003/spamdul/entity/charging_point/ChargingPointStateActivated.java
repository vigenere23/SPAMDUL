package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.ChargingCounter;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyActivatedException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;

public class ChargingPointStateActivated implements ChargingPointState {

  private final ChargingPoint chargingPoint;
  private final ChargingCounter counter;

  public ChargingPointStateActivated(ChargingPoint chargingPoint, ChargingCounter counter) {
    this.chargingPoint = chargingPoint;
    this.counter = counter;
  }

  @Override public void activate(RechargULCard card) {
    throw new ChargingPointAlreadyActivatedException();
  }

  @Override public void connect() {
    counter.start();
    chargingPoint.setState(new ChargingPointStateCharging(chargingPoint, counter));
  }

  @Override public void disconnect() {
    chargingPoint.setState(new ChargingPointStateIdle(chargingPoint));
  }

  @Override public String toString() {
    return "activated";
  }
}
