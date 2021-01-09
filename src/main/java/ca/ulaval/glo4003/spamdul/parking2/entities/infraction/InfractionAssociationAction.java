package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class InfractionAssociationAction {

  private final AccountId accountId;
  private final Infraction infraction;

  public InfractionAssociationAction(AccountId accountId,
                                     Infraction infraction) {
    this.accountId = accountId;
    this.infraction = infraction;
  }

  public void trigger(InfractionAssociator infractionAssociator) {
    infractionAssociator.associateInfraction(accountId, infraction);
  }
}
