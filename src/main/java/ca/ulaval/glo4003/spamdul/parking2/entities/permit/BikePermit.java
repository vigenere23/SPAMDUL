package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import java.time.LocalDateTime;

public class BikePermit extends Permit {

  public BikePermit(PermitNumber permitNumber) {
    super(permitNumber);
  }

  @Override
  public void validateAccess(LocalDateTime accessDateTime, ParkingZone parkingZone) {
    if (!parkingZone.equals(ParkingZone.BIKE)) {
      throw new InvalidParkingZoneException(parkingZone);
    }
  }
}
