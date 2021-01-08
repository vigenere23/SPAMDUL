package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;

public interface ParkingCarFeePopulator {

  void populate(ParkingCarFeeRepository parkingCarFeeRepository);
}
