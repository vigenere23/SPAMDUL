package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.PermitCreationInfosCar;

public class PermitCreationInfos {

  private final String carBrand;
  private final String carModel;
  private final int carYear;
  private final LicensePlate licensePlate;
  private final CarType carType;

  public PermitCreationInfos(String carBrand,
                             String carModel,
                             int carYear,
                             LicensePlate licensePlate, CarType carType) {
    this.carBrand = carBrand;
    this.carModel = carModel;
    this.carYear = carYear;
    this.licensePlate = licensePlate;
    this.carType = carType;
  }

  public PermitCreationInfosCar forCar() {
    return new PermitCreationInfosCar(carBrand, carModel, carYear, licensePlate, carType);
  }
}
