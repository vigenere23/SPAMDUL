package ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.exceptions.InvalidCarTypeArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.exceptions.InvalidCarYearArgumentException;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.car.CarRequest;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.car.CarDto;

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
      throw new InvalidCarTypeArgumentException();
    }
  }

  private void setCarYear(CarRequest carRequest, CarDto carDto) {
    try {
      carDto.year = Integer.parseInt(carRequest.year);

    } catch (NumberFormatException e) {
      throw new InvalidCarYearArgumentException();
    }
  }
}
