package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.CarCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.CarCreationDto;

public class CarCreationAssembler {

  public CarCreationDto fromRequest(CarCreationRequest request) {
    CarCreationDto dto = new CarCreationDto();
    dto.brand = request.brand;
    dto.model = request.model;
    dto.year = request.year;
    dto.carType = CarType.valueOf(request.carType.toUpperCase());
    dto.userId = ParkingUserId.valueOf(request.userId);
    dto.licensePlate = LicensePlate.valueOf(request.licensePlate);

    return dto;
  }
}
