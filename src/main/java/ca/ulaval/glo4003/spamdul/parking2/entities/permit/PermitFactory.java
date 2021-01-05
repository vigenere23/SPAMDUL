package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.PermitCreationInfosCar;

public class PermitFactory {

  private final PermitNumberFactory permitNumberFactory;
  private final CarFactory carFactory;

  public PermitFactory(PermitNumberFactory permitNumberFactory,
                       CarFactory carFactory) {
    this.permitNumberFactory = permitNumberFactory;
    this.carFactory = carFactory;
  }

  public Permit create(PermitType permitType, PermitCreationInfos infos) {
    switch (permitType) {
      case CAR:
        PermitCreationInfosCar carInfos = infos.forCar();
        Car car = carFactory.create(carInfos.getBrand(),
                                    carInfos.getModel(),
                                    carInfos.getYear(),
                                    carInfos.getLicensePlate(),
                                    carInfos.getType());
        return new CarPermit(permitNumberFactory.create(), car);
      case BIKE:
        return new BikePermit(permitNumberFactory.create());
      default:
        throw new RuntimeException(String.format("No permit for type %s", permitType));
    }
  }
}
