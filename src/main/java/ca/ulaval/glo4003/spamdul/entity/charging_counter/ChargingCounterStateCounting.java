package ca.ulaval.glo4003.spamdul.entity.charging_counter;

import ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions.ChargingCounterAlreadyStartedException;
import java.time.Duration;
import java.time.LocalDateTime;

public class ChargingCounterStateCounting implements ChargingCounterState {

  private final ChargingCounter chargingCounter;
  private final LocalDateTime startTime;

  public ChargingCounterStateCounting(ChargingCounter chargingCounter,
                                      LocalDateTime startTime) {
    this.chargingCounter = chargingCounter;
    this.startTime = startTime;
  }

  @Override public void start() {
    throw new ChargingCounterAlreadyStartedException();
  }

  @Override public void stop() {
    LocalDateTime endTime = LocalDateTime.now();
    long hours = Duration.between(startTime, endTime).toHours() + 1; // upper rounding
    chargingCounter.pay(hours);
    chargingCounter.setState(new ChargingCounterStateIdle(chargingCounter));
  }
}
