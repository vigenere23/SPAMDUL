package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

public class ParkingAccessLogIdFactory {

  private final IdGenerator idGenerator;

  public ParkingAccessLogIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ParkingAccessLogId create() {
    return ParkingAccessLogId.valueOf(idGenerator.generate());
  }
}
