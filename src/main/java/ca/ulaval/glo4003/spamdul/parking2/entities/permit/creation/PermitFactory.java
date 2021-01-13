package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;

public class PermitFactory {

  private final PermitFactoryCar permitFactoryCar;
  private final PermitFactoryBike permitFactoryBike;

  public PermitFactory(PermitFactoryCar permitFactoryCar,
                       PermitFactoryBike permitFactoryBike) {
    this.permitFactoryCar = permitFactoryCar;
    this.permitFactoryBike = permitFactoryBike;
  }

  public Permit create(PermitType permitType, PermitCreationInfos infos) {
    switch (permitType) {
      case CAR:
        return permitFactoryCar.create(infos.forCar());
      case BIKE:
        return permitFactoryBike.create();
      default:
        throw new IllegalArgumentException(String.format("No permit for type %s", permitType));
    }
  }
}
