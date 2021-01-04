package ca.ulaval.glo4003.spamdul.parking2.entities.car;

public class CarFactory {

  public Car create(String brand, String model, int year, LicensePlate licensePlate, CarType carType) {
    return new Car(brand, model, year, licensePlate, carType);
  }
}
