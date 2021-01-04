package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

public class PermitFactory {

  private final PermitNumberFactory permitNumberFactory;

  public PermitFactory(PermitNumberFactory permitNumberFactory) {
    this.permitNumberFactory = permitNumberFactory;
  }

  public Permit create(PermitType permitType) {
    switch (permitType) {
      case CAR:
        return new CarPermit(permitNumberFactory.create());
      case BIKE:
        return new BikePermit(permitNumberFactory.create());
      default:
        throw new RuntimeException(String.format("No permit for type %s", permitType));
    }
  }
}
