package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;

public class AccessRightAssociationAction {

  private final LicensePlate licensePlate;
  private final AccessRight accessRight;

  public AccessRightAssociationAction(LicensePlate licensePlate,
                                      AccessRight accessRight) {
    this.licensePlate = licensePlate;
    this.accessRight = accessRight;
  }

  public void trigger(AccessRightAssociator accessRightAssociator) {
    accessRightAssociator.associateAccessRight(licensePlate, accessRight);
  }
}
