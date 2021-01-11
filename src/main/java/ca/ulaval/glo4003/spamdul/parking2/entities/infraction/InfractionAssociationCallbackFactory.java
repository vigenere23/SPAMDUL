package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;

public class InfractionAssociationCallbackFactory {

  private final InfractionAssociator infractionAssociator;

  public InfractionAssociationCallbackFactory(InfractionAssociator infractionAssociator) {
    this.infractionAssociator = infractionAssociator;
  }

  public InfractionAssociationCallback create(AccountId accountId, Infraction infraction) {
    return new InfractionAssociationCallback(infractionAssociator, accountId, infraction);
  }
}
