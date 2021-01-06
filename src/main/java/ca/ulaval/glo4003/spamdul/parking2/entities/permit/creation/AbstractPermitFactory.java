package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public abstract class AbstractPermitFactory {

  protected final PermitNumberFactory permitNumberFactory;

  protected AbstractPermitFactory(PermitNumberFactory permitNumberFactory) {
    this.permitNumberFactory = permitNumberFactory;
  }

  public abstract Permit create(PermitCreationInfos infos);
}
