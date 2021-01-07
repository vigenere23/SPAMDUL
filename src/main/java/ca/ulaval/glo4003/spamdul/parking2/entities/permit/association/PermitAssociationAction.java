package ca.ulaval.glo4003.spamdul.parking2.entities.permit.association;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;

public class PermitAssociationAction {

  private final ParkingUserId parkingUserId;
  private final Permit permit;

  public PermitAssociationAction(ParkingUserId parkingUserId,
                                 Permit permit) {
    this.parkingUserId = parkingUserId;
    this.permit = permit;
  }

  public void trigger(PermitAssociator permitAssociator) {
    permitAssociator.associatePermit(parkingUserId, permit);
  }
}
