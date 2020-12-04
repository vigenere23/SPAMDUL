package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;

public class ParkingAccessLogFactory {

  private final ParkingAccessLogIdFactory parkingAccessLogIdFactory;

  public ParkingAccessLogFactory(ParkingAccessLogIdFactory parkingAccessLogIdFactory) {
    this.parkingAccessLogIdFactory = parkingAccessLogIdFactory;
  }

  public ParkingAccessLog create(ParkingZone zone, LocalDate accessDate) {
    return new ParkingAccessLog(parkingAccessLogIdFactory.create(), zone, accessDate);
  }
}
