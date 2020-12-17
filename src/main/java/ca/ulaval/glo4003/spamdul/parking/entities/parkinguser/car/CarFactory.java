package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car;

import java.time.LocalDate;

public class CarFactory {

  private final CarIdFactory carIdFactory;

  public CarFactory(CarIdFactory carIdFactory) {
    this.carIdFactory = carIdFactory;
  }

  public Car create(CarType carType, String brand, String model, int year, String licensePlate) {
    if (year > LocalDate.now().getYear()) {
      throw new InvalidCarYearException();
    }
    return new Car(carIdFactory.create(), carType, brand, model, year, new LicensePlate(licensePlate));
  }
}
