package ca.ulaval.glo4003.spamdul.usecases.user.car;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;

public class CarService {

  private final CarFactory carfactory;

  public CarService(CarFactory carfactory) {
    this.carfactory = carfactory;
  }

  //  public Car createCar(CarDto carDto) {
  //    return carfactory.create(carDto.carType, carDto.brand, carDto.model, carDto.year, carDto.licensePlate);
  //  }
}
