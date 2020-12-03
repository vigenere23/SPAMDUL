package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidInfractionIdException;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionIdFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;

public class InfractionAssembler {

  public PassToValidateDto fromRequest(InfractionRequest infractionRequest) {
    PassToValidateDto dto = new PassToValidateDto();

    dto.passCode = infractionRequest.passCode;
    dto.parkingZone = getParkingZone(infractionRequest);

    return dto;
  }

  private ParkingZone getParkingZone(InfractionRequest infractionRequest) {
    try {
      return ParkingZone.valueOf(infractionRequest.parkingZone.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidInfractionParkingZoneException();
    }
  }

  public InfractionResponse toResponse(Infraction infraction) {
    if (infraction == null) {
      return null;
    }

    InfractionResponse infractionResponse = new InfractionResponse();
    infractionResponse.infractionId = infraction.getInfractionId().toString();
    infractionResponse.amount = infraction.getAmount();
    infractionResponse.code = infraction.getCode().toString();
    infractionResponse.reason = infraction.getInfractionDescription();

    return infractionResponse;
  }

  public InfractionPaymentDto fromRequest(InfractionPaymentRequest infractionPaymentRequest) {
    InfractionPaymentDto infractionPaymentDto = new InfractionPaymentDto();

    infractionPaymentDto.infractionId = getInfractionId(infractionPaymentRequest);

    return infractionPaymentDto;
  }

  private InfractionId getInfractionId(InfractionPaymentRequest infractionPaymentRequest) {
    try {
      return InfractionId.valueOf(infractionPaymentRequest.infractionId.toUpperCase());

    } catch (InvalidInfractionIdException e) {
      throw new InvalidInfractionIdFormatException();
    }
  }
}
