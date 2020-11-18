package ca.ulaval.glo4003.spamdul.entity.charging_counter;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions.ChargingCounterNotStartedException;
import java.time.LocalDateTime;

public class ChargingCounterStateIdle implements ChargingCounterState {

  private final ChargingCounter chargingCounter;

  public ChargingCounterStateIdle(ChargingCounter chargingCounter) {
    this.chargingCounter = chargingCounter;
  }

  @Override public void start() {
    LocalDateTime startTime = LocalDateTime.now();
    chargingCounter.setState(new ChargingCounterStateCounting(chargingCounter, startTime));
  }

  @Override public void stop() {
    throw new ChargingCounterNotStartedException();
  }
}
