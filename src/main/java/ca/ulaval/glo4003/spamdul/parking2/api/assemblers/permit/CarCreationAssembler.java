package ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.permit.CarCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.permit.CarCreationDto;

public class CarCreationAssembler {

  public CarCreationDto fromRequest(CarCreationRequest request) {
    CarCreationDto dto = new CarCreationDto();
    dto.brand = request.brand;
    dto.model = request.model;
    dto.year = request.year;
    dto.type = CarType.valueOf(request.type.toUpperCase());
    dto.licensePlate = LicensePlate.valueOf(request.licensePlate);
    return dto;
  }
}
