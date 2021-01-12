package ca.ulaval.glo4003.spamdul.parking2.entities.permit.callbacks;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitAssociator;

public class PermitAssociationCallbackFactory {

  private final PermitAssociator permitAssociator;

  public PermitAssociationCallbackFactory(PermitAssociator permitAssociator) {
    this.permitAssociator = permitAssociator;
  }

  public PermitAssociationCallback create(AccountId accountId, Permit permit) {
    return new PermitAssociationCallback(permitAssociator, accountId, permit);
  }
}
