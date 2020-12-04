package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class ChargingPointIdFactory {

  private final IdGenerator<Long> idGenerator;

  public ChargingPointIdFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ChargingPointId create() {
    return ChargingPointId.valueOf(String.valueOf(idGenerator.generateId()));
  }
}
