package ca.ulaval.glo4003.spamdul.parking2.entities.permit.association;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public class PermitAssociationAction {

  private final AccountId accountId;
  private final Permit permit;

  public PermitAssociationAction(AccountId accountId,
                                 Permit permit) {
    this.accountId = accountId;
    this.permit = permit;
  }

  public void trigger(PermitAssociator permitAssociator) {
    permitAssociator.associatePermit(accountId, permit);
  }
}
