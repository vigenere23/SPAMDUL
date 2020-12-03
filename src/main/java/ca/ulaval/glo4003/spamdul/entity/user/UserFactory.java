package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;
import ca.ulaval.glo4003.spamdul.usecases.parking.car.CarDto;
import java.time.LocalDate;

public class UserFactory {

  private final CarFactory carFactory;

  public UserFactory(CarFactory carFactory) {
    this.carFactory = carFactory;
  }

  public User create(String name, Gender gender, LocalDate birthDate, CarDto carDto) {
    Car car = carFactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);

    return new User(new UserId(), name, gender, birthDate, car);
  }
}
