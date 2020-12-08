package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;
import ca.ulaval.glo4003.spamdul.usecases.user.car.CarDto;
import java.time.LocalDate;

public class UserFactory {

  private final UserIdFactory userIdFactory;
  private final CarFactory carFactory;

  public UserFactory(UserIdFactory userIdFactory, CarFactory carFactory) {
    this.userIdFactory = userIdFactory;
    this.carFactory = carFactory;
  }

  public User create(String name, Gender gender, LocalDate birthDate, CarDto carDto) {
    if (carDto != null) {
      Car car = carFactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);

      return new User(userIdFactory.create(), name, gender, birthDate, car);
    }

    return new User(userIdFactory.create(), name, gender, birthDate);
  }
}
