package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import java.time.LocalDateTime;

public class BikePermit extends Permit {

  protected BikePermit(PermitNumber permitNumber) {
    super(permitNumber);
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime) {
    // Do nothing - access is valid 24/7
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (!parkingZone.equals(ParkingZone.BIKE)) {
      throw new InvalidAccessException();
    }

    validateAccess(accessDateTime);
  }
}
