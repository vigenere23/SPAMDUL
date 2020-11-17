package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import java.util.Optional;

public class ChargingPoint {

  private final ChargingPointId id;
  private Optional<ChargingCounter> counter = Optional.empty();

  public ChargingPoint(ChargingPointId id) {
    this.id = id;
  }

  public void activate(RechargULCard card) {
    verifyNotActivated();

    counter = Optional.of(new ChargingCounter(card));
  }

  public void connect() {
    verifyActivated();

    counter.get().start();
  }

  public void disconnect() {
    verifyActivated();

    counter.get().stop();
    counter = Optional.empty();
  }

  public ChargingPointId getId() {
    return id;
  }

  private void verifyNotActivated() {
    if (counter.isPresent()) {
      throw new RuntimeException("Charging point is already activated");
    }
  }

  private void verifyActivated() {
    if (!counter.isPresent()) {
      throw new RuntimeException("Charging point is not activated");
    }
  }
}
