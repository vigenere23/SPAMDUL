package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.ChargingCounter;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyChargingException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotDisconnectedException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;

public class ChargingPointStateCharging implements ChargingPointState {

  private final ChargingPoint chargingPoint;
  private final ChargingCounter counter;

  public ChargingPointStateCharging(ChargingPoint chargingPoint, ChargingCounter counter) {
    this.chargingPoint = chargingPoint;
    this.counter = counter;
  }

  @Override public void activate(RechargULCard card) {
    throw new ChargingPointNotDisconnectedException();
  }

  @Override public void connect() {
    throw new ChargingPointAlreadyChargingException();
  }

  @Override public void disconnect() {
    counter.stop();
    chargingPoint.setState(new ChargingPointStateIdle(chargingPoint));
  }
}
