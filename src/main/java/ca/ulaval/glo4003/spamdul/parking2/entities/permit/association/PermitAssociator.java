package ca.ulaval.glo4003.spamdul.parking2.entities.permit.association;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;

public class PermitAssociator {

  private final ParkingUserRepository parkingUserRepository;

  public PermitAssociator(ParkingUserRepository parkingUserRepository) {
    this.parkingUserRepository = parkingUserRepository;
  }

  public void associatePermit(ParkingUserId parkingUserId, Permit permit) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    parkingUser.addPermit(permit);
    parkingUserRepository.save(parkingUser);
  }
}
