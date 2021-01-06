package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.CarResponse;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.CarDto;

public class CarDtoAssembler {

  public CarResponse toResponse(CarDto dto) {
    CarResponse response = new CarResponse();
    response.brand = dto.brand;
    response.model = dto.model;
    response.year = dto.year;
    response.licensePlate = dto.licensePlate.toString();
    response.type = dto.type.toString();

    return response;
  }
}
