package ca.ulaval.glo4003.spamdul.parking2.entities.access.right.association;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRight;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;

public class AccessRightAssociationCallbackFactory {

  private final AccessRightAssociator accessRightAssociator;

  public AccessRightAssociationCallbackFactory(AccessRightAssociator accessRightAssociator) {
    this.accessRightAssociator = accessRightAssociator;
  }

  public AccessRightAssociationCallback create(LicensePlate licensePlate, AccessRight accessRight) {
    return new AccessRightAssociationCallback(accessRightAssociator, licensePlate, accessRight);
  }
}
