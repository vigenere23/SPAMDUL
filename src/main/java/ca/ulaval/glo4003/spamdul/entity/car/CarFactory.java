package ca.ulaval.glo4003.spamdul.entity.car;

import java.time.LocalDate;

public class CarFactory {


  public Car create(CarType carType, String brand, String model, int year, String licensePlate) {
    if (year > LocalDate.now().getYear()) {
      throw new InvalidCarYearException(String.format("The models for %s are not available yet", year));
    }
    return new Car(new CarId(), carType, brand, model, year, licensePlate);
  }
}
