package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;

public class InfractionAssembler {

  public PassToValidateDto fromRequest(InfractionRequest infractionRequest) {
    PassToValidateDto dto = new PassToValidateDto();

    dto.passCode = infractionRequest.passCode;
    dto.parkingZone = getParkingZone(infractionRequest);
    dto.licensePlate = new LicensePlate(infractionRequest.licensePlate);

    return dto;
  }

  private ParkingZone getParkingZone(InfractionRequest infractionRequest) {
    try {
      return ParkingZone.valueOf(infractionRequest.parkingZone.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidInfractionParkingZoneException();
    }
  }

  public InfractionResponse toResponse(InfractionDto infractionDto) {
    if (infractionDto == null) {
      return null;
    }

    InfractionResponse infractionResponse = new InfractionResponse();
    infractionResponse.infractionId = infractionDto.id.toString();
    infractionResponse.amount = infractionDto.amount.asDouble();
    infractionResponse.code = infractionDto.code.toString();
    infractionResponse.reason = infractionDto.infractionDescription;

    return infractionResponse;
  }

  public InfractionPaymentDto fromRequest(InfractionPaymentRequest infractionPaymentRequest) {
    InfractionPaymentDto infractionPaymentDto = new InfractionPaymentDto();

    infractionPaymentDto.infractionId = getInfractionId(infractionPaymentRequest);

    return infractionPaymentDto;
  }

  private InfractionId getInfractionId(InfractionPaymentRequest infractionPaymentRequest) {
    return InfractionId.valueOf(infractionPaymentRequest.infractionId.toUpperCase());
  }
}
