package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;

public interface ChargingPointState {

  void activate(RechargULCard card);

  void connect();

  void disconnect();
}
