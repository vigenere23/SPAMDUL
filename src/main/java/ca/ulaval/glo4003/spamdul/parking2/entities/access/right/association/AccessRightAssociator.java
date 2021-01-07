package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;

public class AccessRightAssociator {

  private final ParkingUserRepository parkingUserRepository;

  public AccessRightAssociator(ParkingUserRepository parkingUserRepository) {
    this.parkingUserRepository = parkingUserRepository;
  }

  public void associateAccessRight(ParkingUserId parkingUserId, LicensePlate licensePlate, AccessRight accessRight) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    parkingUser.addAccessRight(licensePlate, accessRight);
    parkingUserRepository.save(parkingUser);
  }
}
