package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.BikePermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public class PermitFactoryBike {

  private final PermitNumberFactory permitNumberFactory;

  public PermitFactoryBike(PermitNumberFactory permitNumberFactory) {
    this.permitNumberFactory = permitNumberFactory;
  }

  public Permit create() {
    return new BikePermit(permitNumberFactory.create());
  }
}
