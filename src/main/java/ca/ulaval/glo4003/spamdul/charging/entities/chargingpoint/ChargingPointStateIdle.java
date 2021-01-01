package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointNotActivatedException;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointNotChargingException;
import ca.ulaval.glo4003.spamdul.shared.entities.counter.MillisecondsCounter;
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
