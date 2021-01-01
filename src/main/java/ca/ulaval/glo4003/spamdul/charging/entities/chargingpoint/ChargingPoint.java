package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;

public class ChargingPoint {

  private final ChargingPointId id;

  private RechargULCardId currentCardId = null;
  private ChargingPointState state = new ChargingPointStateIdle(this);

  public ChargingPoint(ChargingPointId id) {
    this.id = id;
  }

  public void activate(EnoughCreditForChargingVerifier enoughCreditForChargingVerifier,
                       RechargULCardId rechargULCardId) {
    enoughCreditForChargingVerifier.verify(rechargULCardId);
    this.currentCardId = rechargULCardId;
    state.activate();
  }

  public void connect() {
    state.connect();
  }

  public void disconnect() {
    state.disconnect();
  }

  public void deactivateAndPay(ChargingPaymentService chargingPaymentService) {
    long millisecondsUsed = state.deactivate();
    chargingPaymentService.pay(millisecondsUsed, currentCardId);
    currentCardId = null;
  }

  public void setState(ChargingPointState state) {
    this.state = state;
  }

  public ChargingPointId getId() {
    return id;
  }

  public String getState() {
    return state.toString();
  }
}
