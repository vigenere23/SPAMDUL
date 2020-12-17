package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;

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
