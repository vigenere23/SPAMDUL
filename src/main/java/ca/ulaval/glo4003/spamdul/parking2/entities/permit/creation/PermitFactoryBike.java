package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.BikePermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public class PermitFactoryBike extends AbstractPermitFactory {

  public PermitFactoryBike(PermitNumberFactory permitNumberFactory) {
    super(permitNumberFactory);
  }

  @Override
  public Permit create(PermitCreationInfos infos) {
    return new BikePermit(permitNumberFactory.create());
  }
}
