package ca.ulaval.glo4003.spamdul.entity.charging;

import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.entity.charging.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.utils.counter.MillisecondsCounter;
import java.time.Clock;

public class ChargingPointStateIdle implements ChargingPointState {

  private final ChargingPoint chargingPoint;

  public ChargingPointStateIdle(ChargingPoint chargingPoint) {
    this.chargingPoint = chargingPoint;
  }

  @Override public void activate() {
    MillisecondsCounter counter = new MillisecondsCounter(Clock.systemDefaultZone());
    chargingPoint.setState(new ChargingPointStateActivated(chargingPoint, counter, 0));
  }

  @Override public void connect() {
    throw new ChargingPointNotActivatedException();
  }

  @Override public void disconnect() {
    throw new ChargingPointNotChargingException();
  }

  @Override public long deactivate() {
    throw new ChargingPointNotActivatedException();
  }

  @Override public String toString() {
    return "idle";
  }
}
