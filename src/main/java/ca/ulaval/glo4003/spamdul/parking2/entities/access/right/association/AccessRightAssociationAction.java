package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;

public class AccessRightAssociationAction {

  private final ParkingUserId parkingUserId;
  private final LicensePlate licensePlate;
  private final AccessRight accessRight;

  public AccessRightAssociationAction(ParkingUserId parkingUserId,
                                      LicensePlate licensePlate,
                                      AccessRight accessRight) {
    this.parkingUserId = parkingUserId;
    this.licensePlate = licensePlate;
    this.accessRight = accessRight;
  }

  public void trigger(AccessRightAssociator accessRightAssociator) {
    accessRightAssociator.associateAccessRight(parkingUserId, licensePlate, accessRight);
  }
}
