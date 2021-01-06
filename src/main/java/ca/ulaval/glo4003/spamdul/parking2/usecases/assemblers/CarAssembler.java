package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.Car;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.CarDto;

public class CarAssembler {

  public CarDto toDto(Car car) {
    CarDto dto = new CarDto();
    dto.brand = car.getBrand();
    dto.model = car.getModel();
    dto.year = car.getYear();
    dto.licensePlate = car.getLicensePlate();
    dto.type = car.getType();

    return dto;
  }
}
