package ca.ulaval.glo4003.spamdul.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.dto.InfractionDto;

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
