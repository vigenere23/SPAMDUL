package ca.ulaval.glo4003.spamdul.parking2.entities.car;

public class CarFactory {

  public Car create(LicensePlate licensePlate, CarType carType) {
    return new Car(licensePlate, carType);
  }
}
