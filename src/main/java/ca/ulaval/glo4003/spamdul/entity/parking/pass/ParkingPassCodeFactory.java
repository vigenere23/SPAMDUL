package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;

public class ParkingPassCodeFactory {

  private final IdGenerator idGenerator;

  public ParkingPassCodeFactory(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ParkingPassCode create(ParkingZone parkingZone) {
    if (parkingZone == ParkingZone.ZONE_BIKE) {
      return BikeParkingPassCode.valueOf(idGenerator.generate());
    }

    return CarParkingPassCode.valueOf(idGenerator.generate());
  }
}
