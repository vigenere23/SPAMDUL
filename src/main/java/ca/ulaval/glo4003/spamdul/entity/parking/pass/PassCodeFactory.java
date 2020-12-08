package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess.BikeParkingAccessCode;

public class PassCodeFactory {

  private final IdGenerator idGenerator;

  public PassCodeFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public PassCode create() {
    return PassCode.valueOf(String.valueOf(idGenerator.generate()));
  }

  public PassCode create(ParkingZone parkingZone) {
    if (parkingZone == ParkingZone.ZONE_BIKE) {
      return BikeParkingAccessCode.valueOf(String.valueOf(idGenerator.generate()));
    }

    return PassCode.valueOf(String.valueOf(idGenerator.generate()));
  }
}
