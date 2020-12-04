package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;

public class ParkingAccessLogIdFactory {

  private final IdGenerator<Long> idGenerator;

  public ParkingAccessLogIdFactory(IdGenerator<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ParkingAccessLogId create() {
    return ParkingAccessLogId.valueOf(String.valueOf(idGenerator.getNextId()));
  }
}
