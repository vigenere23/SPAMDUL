package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;

public interface ParkingZoneFeePopulator {

  void populate(ParkingZoneFeeRepository parkingZoneFeeRepository);
}
