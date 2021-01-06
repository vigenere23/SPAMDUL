package ca.ulaval.glo4003.spamdul.parking2.entities.permit.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.right.AccessRightValidator;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.CarPermit;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.Permit;

public class PermitFactoryCar extends AbstractPermitFactory {

  private final AccessRightValidator accessRightValidator;
  private final CarFactory carFactory;

  public PermitFactoryCar(PermitNumberFactory permitNumberFactory,
                          AccessRightValidator accessRightValidator,
                          CarFactory carFactory) {
    super(permitNumberFactory);
    this.accessRightValidator = accessRightValidator;
    this.carFactory = carFactory;
  }

  @Override
  public Permit create(PermitCreationInfos permitCreationInfos) {
    PermitCreationInfosCar infos = permitCreationInfos.forCar();
    Car car = carFactory.create(infos.getCar().getBrand(),
                                infos.getCar().getModel(),
                                infos.getCar().getYear(),
                                infos.getCar().getLicensePlate(),
                                infos.getCar().getType());
    return new CarPermit(permitNumberFactory.create(), accessRightValidator, car);
  }
}
