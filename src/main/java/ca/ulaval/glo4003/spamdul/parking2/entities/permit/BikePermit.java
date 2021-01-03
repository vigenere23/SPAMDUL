package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import java.time.LocalDateTime;

public class BikePermit extends Permit {

  protected BikePermit(PermitNumber permitNumber) {
    super(permitNumber);
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    // Nothing to do
  }
}
