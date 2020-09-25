package ca.ulaval.glo4003.spamdul.usecases.campusaccess.car;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;

public class CarService {

  private CarFactory carfactory;
  private CarRepository carRepository;
  private UserService userService;

  public CarService(CarFactory carfactory, CarRepository carRepository) {
    this.carfactory = carfactory;
    this.carRepository = carRepository;
  }

  public Car createCar(CarDto carDto) {
    Car car = carfactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);

    return carRepository.save(car);
  }
}
