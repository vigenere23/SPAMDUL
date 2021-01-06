package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;

public class AccessRightBuyingCallback {

  private final ParkingUserId parkingUserId;
  private final LicensePlate licensePlate;
  private final AccessRight accessRight;

  public AccessRightBuyingCallback(ParkingUserId parkingUserId,
                                   LicensePlate licensePlate,
                                   AccessRight accessRight) {
    this.parkingUserId = parkingUserId;
    this.licensePlate = licensePlate;
    this.accessRight = accessRight;
  }

  public void trigger(ParkingUserRepository parkingUserRepository) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    parkingUser.addAccessRight(licensePlate, accessRight);
    parkingUserRepository.save(parkingUser);
  }
}
