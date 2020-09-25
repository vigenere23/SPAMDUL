package ca.ulaval.glo4003.spamdul.usecases.car;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.exceptions.InvalidUserIdArgumentException;

public class CarService {

  private CarFactory carfactory;
  private CarRepository carRepository;
  private UserRepository userRepository;

  public CarService(CarFactory carfactory, CarRepository carRepository, UserRepository userRepository) {
    this.carfactory = carfactory;
    this.carRepository = carRepository;
    this.userRepository = userRepository;
  }

  public Car createCar(CarDto carDto) {
//    try {
//      userRepository.findById(carDto.userId);
//    } catch (UserNotFoundException e) {
//      throw new InvalidUserIdArgumentException(e.getMessage());
//    }

    Car car = carfactory.create(carDto.brand, carDto.model, carDto.year, carDto.licensePlate, carDto.userId);

    return carRepository.save(car);
  }

}
