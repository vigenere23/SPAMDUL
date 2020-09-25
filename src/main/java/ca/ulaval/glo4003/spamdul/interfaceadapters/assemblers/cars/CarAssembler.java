package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.car.CarRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.exceptions.InvalidCarYearArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.cars.exceptions.InvalidUserIdArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.car.CarDto;

public class CarAssembler {

  public CarDto fromDto(CarRequest carRequest) {
    CarDto carDto = new CarDto();
    carDto.brand = carRequest.brand;
    carDto.model = carRequest.model;
    carDto.licensePlate = carRequest.licensePlate;

    try {
      carDto.year = Integer.parseInt(carRequest.year);
    } catch (NumberFormatException e) {
      throw new InvalidCarYearArgumentException("Must provide a valid year");
    }

    try {
      carDto.userId = UserId.valueOf(carRequest.userId);
    } catch (NumberFormatException e) {
      throw new InvalidUserIdArgumentException("Must provide a valid user id");
    }

    return carDto;
  }
}
