package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.ChargingCounter;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;

public class ChargingPointStateIdle implements ChargingPointState {

  private final ChargingPoint chargingPoint;

  public ChargingPointStateIdle(ChargingPoint chargingPoint) {
    this.chargingPoint = chargingPoint;
  }

  @Override public void activate(RechargULCard card) {
    ChargingCounter counter = new ChargingCounter(card);
    chargingPoint.setState(new ChargingPointStateActivated(chargingPoint, counter));
  }

  @Override public void connect() {
    throw new ChargingPointNotActivatedException();
  }

  @Override public void disconnect() {
    throw new ChargingPointNotChargingException();
  }

  @Override public String toString() {
    return "idle";
  }
}
