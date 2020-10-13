package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.InvalidPassCodeFormat;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionPassCodeFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionTimeOfTheDayException;
import ca.ulaval.glo4003.spamdul.usecases.InfractionService.InfractionValidationDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InfractionAssembler {

  public InfractionValidationDto fromRequest(InfractionRequest infractionRequest) {
    InfractionValidationDto dto = new InfractionValidationDto();

    dto.passCode = getPassCode(infractionRequest, dto);
    dto.parkingZone = getParkingZone(infractionRequest, dto);
    dto.time = getTimeOfTheDay(infractionRequest, dto);

    return dto;
  }

  private LocalTime getTimeOfTheDay(InfractionRequest infractionRequest, InfractionValidationDto dto) {
    try {
      return LocalTime.parse(infractionRequest.timeOfTheDay, DateTimeFormatter.LOCAL_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidInfractionTimeOfTheDayException("The time of the day must be HH:mm");
    }
  }

  private ParkingZone getParkingZone(InfractionRequest infractionRequest, InfractionValidationDto dto) {
    try {
      return ParkingZone.valueOf(infractionRequest.parkingZone.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidInfractionParkingZoneException("Invalid parking zone");
    }
  }

  private PassCode getPassCode(InfractionRequest infractionRequest, InfractionValidationDto dto) {
    try {
      return PassCode.valueOf(infractionRequest.passCode);
    } catch (InvalidPassCodeFormat e) {
      throw new InvalidInfractionPassCodeFormatException("Invalid pass code format");
    }
  }

  public InfractionResponse toResponse(Infraction infraction) {
    InfractionResponse response = new InfractionResponse();
    response.amount = infraction.getAmount();
    response.code = infraction.getCode().toString();
    response.reason = infraction.getInfractionDscription();

    return response;
  }
}
