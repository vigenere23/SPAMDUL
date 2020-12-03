package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;

public class UserFactory {

  private CarFactory carFactory;

  public UserFactory(CarFactory carFactory) {
    this.carFactory = carFactory;
  }

  public User create(UserDto userDto) {
    Car car = carFactory.create(userDto.carDto);

    return new User(new UserId(), userDto.name, userDto.gender, userDto.birthDate, car);
  }
}
