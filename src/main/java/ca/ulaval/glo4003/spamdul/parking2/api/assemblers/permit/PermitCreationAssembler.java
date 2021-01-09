package ca.ulaval.glo4003.spamdul.parking2.api.assemblers.permit;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.permit.PermitCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.PermitCreationDto;

public class PermitCreationAssembler {

  private final PermitDeliveryAssembler permitDeliveryAssembler;
  private final CarCreationAssembler carCreationAssembler;

  public PermitCreationAssembler(PermitDeliveryAssembler permitDeliveryAssembler,
                                 CarCreationAssembler carCreationAssembler) {
    this.permitDeliveryAssembler = permitDeliveryAssembler;
    this.carCreationAssembler = carCreationAssembler;
  }

  public PermitCreationDto fromRequest(PermitCreationRequest request) {
    PermitCreationDto dto = new PermitCreationDto();
    dto.type = PermitType.valueOf(request.type.toUpperCase());
    dto.delivery = permitDeliveryAssembler.fromRequest(request.delivery);

    if (request.car != null) {
      dto.car = carCreationAssembler.fromRequest(request.car);
    }

    return dto;
  }
}
