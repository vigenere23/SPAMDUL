package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.validation.AccessRightValidator;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.CarPermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public class PermitFactoryCar {

  private final PermitNumberFactory permitNumberFactory;
  private final AccessRightValidator accessRightValidator;
  private final CarFactory carFactory;

  public PermitFactoryCar(PermitNumberFactory permitNumberFactory,
                          AccessRightValidator accessRightValidator,
                          CarFactory carFactory) {
    this.permitNumberFactory = permitNumberFactory;
    this.accessRightValidator = accessRightValidator;
    this.carFactory = carFactory;
  }

  public Permit create(PermitCreationInfosCar infos) {
    Car car = carFactory.create(infos.getCar().getBrand(),
                                infos.getCar().getModel(),
                                infos.getCar().getYear(),
                                infos.getCar().getLicensePlate(),
                                infos.getCar().getType());
    return new CarPermit(permitNumberFactory.create(), accessRightValidator, car);
  }
}
