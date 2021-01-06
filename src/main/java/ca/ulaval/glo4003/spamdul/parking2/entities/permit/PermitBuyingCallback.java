package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;

public class PermitBuyingCallback {

  private final ParkingUserId parkingUserId;
  private final Permit permit;

  public PermitBuyingCallback(ParkingUserId parkingUserId,
                              Permit permit) {
    this.parkingUserId = parkingUserId;
    this.permit = permit;
  }

  public void trigger(ParkingUserRepository parkingUserRepository) {
    ParkingUser parkingUser = parkingUserRepository.findBy(parkingUserId);
    parkingUser.addPermit(permit);
    parkingUserRepository.save(parkingUser);
  }
}
