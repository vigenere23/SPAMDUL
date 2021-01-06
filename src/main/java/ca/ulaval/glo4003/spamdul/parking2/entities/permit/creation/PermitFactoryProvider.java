package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;

public class PermitFactoryProvider {

  private final PermitFactoryCar permitFactoryCar;
  private final PermitFactoryBike permitFactoryBike;

  public PermitFactoryProvider(PermitFactoryCar permitFactoryCar,
                               PermitFactoryBike permitFactoryBike) {
    this.permitFactoryCar = permitFactoryCar;
    this.permitFactoryBike = permitFactoryBike;
  }

  public AbstractPermitFactory provide(PermitType permitType) {
    switch (permitType) {
      case CAR:
        return permitFactoryCar;
      case BIKE:
        return permitFactoryBike;
      default:
        throw new IllegalArgumentException(String.format("No permit factory for type %s", permitType));
    }
  }
}
