package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class ChargingPointIdFactory {

  private final IdGenerator idGenerator;

  public ChargingPointIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ChargingPointId create() {
    return ChargingPointId.valueOf(idGenerator.generate());
  }
}
