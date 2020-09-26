package ca.ulaval.glo4003.spamdul.entity.car;

public class CarFactory {


  public Car create(CarType carType, String brand, String model, int year, String licensePlate) {
    return new Car(new CarId(), carType, brand, model, year, licensePlate);
  }
}
