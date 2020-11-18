package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;

public class ChargingPoint implements ChargingPointState {

  private final ChargingPointId id;
  private ChargingPointState state = new ChargingPointStateIdle(this);

  public ChargingPoint(ChargingPointId id) {
    this.id = id;
  }

  @Override public void activate(RechargULCard card) {
    state.activate(card);
  }

  @Override public void connect() {
    state.connect();
  }

  @Override public void disconnect() {
    state.disconnect();
  }

  public void setState(ChargingPointState state) {
    this.state = state;
  }

  public ChargingPointId getId() {
    return id;
  }
}
