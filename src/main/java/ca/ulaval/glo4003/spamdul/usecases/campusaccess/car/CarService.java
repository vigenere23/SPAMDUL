package ca.ulaval.glo4003.spamdul.usecases.campusaccess.car;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarRepository;

public class CarService {

  private CarFactory carfactory;
  private CarRepository carRepository;

  public CarService(CarFactory carfactory, CarRepository carRepository) {
    this.carfactory = carfactory;
    this.carRepository = carRepository;
  }

  public Car createCar(CarDto carDto) {
    return carfactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);
  }

  public void saveCar(Car car) {
    carRepository.save(car);
  }
}
