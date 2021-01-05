package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.PermitCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;

public class PermitCreationAssembler {

  private final PermitDeliveryAssembler permitDeliveryAssembler;

  public PermitCreationAssembler(PermitDeliveryAssembler permitDeliveryAssembler) {
    this.permitDeliveryAssembler = permitDeliveryAssembler;
  }

  public PermitCreationDto fromRequest(PermitCreationRequest request) {
    PermitCreationDto dto = new PermitCreationDto();
    dto.type = PermitType.valueOf(request.type.toUpperCase());

    if (request.car != null) {
      dto.carBrand = request.car.brand;
      dto.carModel = request.car.model;
      dto.carYear = request.car.year;
      dto.carType = CarType.valueOf(request.car.type.toUpperCase());
      dto.licensePlate = LicensePlate.valueOf(request.car.licensePlate);
    }

    dto.delivery = permitDeliveryAssembler.fromRequest(request.delivery);

    return dto;
  }
}
