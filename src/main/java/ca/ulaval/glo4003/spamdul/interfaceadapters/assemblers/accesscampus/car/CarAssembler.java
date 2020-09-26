package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.car;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.car.exceptions.InvalidCarTypeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.car.exceptions.InvalidCarYearArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;

public class CarAssembler {

  public CarDto fromRequest(CarRequest carRequest) {
    CarDto carDto = new CarDto();
    carDto.brand = carRequest.brand;
    carDto.model = carRequest.model;
    carDto.licensePlate = carRequest.licensePlate;

    setCarYear(carRequest, carDto);
    setCarType(carRequest, carDto);

    return carDto;
  }

  private void setCarType(CarRequest carRequest, CarDto carDto) {
    try {
      carDto.carType = CarType.valueOf(carRequest.type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidCarTypeArgumentException(
          "The car type must be gourmande, economique, hybride_economique, super_economique or sans_pollution");
    }
  }

  private void setCarYear(CarRequest carRequest, CarDto carDto) {
    try {
      carDto.year = Integer.parseInt(carRequest.year);
    } catch (NumberFormatException e) {
      throw new InvalidCarYearArgumentException("Must provide a valid year");
    }
  }
}
