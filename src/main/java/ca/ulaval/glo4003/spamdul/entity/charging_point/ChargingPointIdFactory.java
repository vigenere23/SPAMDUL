package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class ChargingPointIdFactory {

  private final IdGenerator idGenerator;

  public ChargingPointIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ChargingPointId create() {
    return ChargingPointId.valueOf(String.valueOf(idGenerator.generate()));
  }
}
