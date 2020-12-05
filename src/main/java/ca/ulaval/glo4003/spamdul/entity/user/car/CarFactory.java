package ca.ulaval.glo4003.spamdul.entity.user.car;

import ca.ulaval.glo4003.spamdul.usecases.user.car.CarDto;
import java.time.LocalDate;

public class CarFactory {


  public Car create(CarDto carDto) {
    if (carDto.year > LocalDate.now().getYear()) {
      throw new InvalidCarYearException();
    }
    return new Car(new CarId(), carDto.carType, carDto.brand, carDto.model,
                   carDto.year, new LicensePlate(carDto.licensePlate));
  }
}
