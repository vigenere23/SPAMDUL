package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class ParkingAccessLogIdFactory {

  private final IdGenerator idGenerator;

  public ParkingAccessLogIdFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ParkingAccessLogId create() {
    return ParkingAccessLogId.valueOf(idGenerator.generate());
  }
}
