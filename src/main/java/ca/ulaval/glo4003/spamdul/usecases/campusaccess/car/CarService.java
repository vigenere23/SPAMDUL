package ca.ulaval.glo4003.spamdul.usecases.campusaccess.car;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarFactory;

public class CarService {

  private CarFactory carfactory;

  public CarService(CarFactory carfactory) {
    this.carfactory = carfactory;
  }

  public Car createCar(CarDto carDto) {
    return carfactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);
  }
}
