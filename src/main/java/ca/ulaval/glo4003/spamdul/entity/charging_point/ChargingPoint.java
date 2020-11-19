package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import java.util.Optional;

public class ChargingPoint {

  private final ChargingPointId id;
  private final ChargingPaymentService chargingPaymentService;

  private Optional<RechargULCard> card = Optional.empty();
  private ChargingPointState state = new ChargingPointStateIdle(this);

  public ChargingPoint(ChargingPointId id, ChargingPaymentService chargingPaymentService) {
    this.id = id;
    this.chargingPaymentService = chargingPaymentService;
  }

  public void activate(RechargULCard card) {
    if (!card.hasEnoughCredits()) {
      throw new NotEnoughCreditsException();
    }

    this.card = Optional.of(card);
    state.activate();
  }

  public void connect() {
    state.connect();
  }

  public void disconnect() {
    state.disconnect();
  }

  public void deactivate() {
    long millisecondsUsed = state.deactivate();
    chargingPaymentService.pay(millisecondsUsed, card.get());
    card = Optional.empty();
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
