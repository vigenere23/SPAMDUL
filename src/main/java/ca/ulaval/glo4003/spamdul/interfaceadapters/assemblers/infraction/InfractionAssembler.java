package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidInfractionIdException;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.InvalidPassCodeFormat;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPayRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.EmptyPassCodeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionIdFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionPassCodeFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionTimeOfTheDayException;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPayDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionValidationDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InfractionAssembler {

  public InfractionValidationDto fromRequest(InfractionRequest infractionRequest) {
    InfractionValidationDto dto = new InfractionValidationDto();

    dto.passCode = getPassCode(infractionRequest);
    dto.parkingZone = getParkingZone(infractionRequest);
    dto.time = getTimeOfTheDay(infractionRequest);

    return dto;
  }

  public InfractionPayDto fromRequest(InfractionPayRequest infractionPayRequest){
    InfractionPayDto infractionPayDto = new InfractionPayDto();

    infractionPayDto.infractionId = getInfractionId(infractionPayRequest);

    return  infractionPayDto;
  }

  private InfractionId getInfractionId(InfractionPayRequest infractionPayRequest) {
    try{
      return InfractionId.valueOf(infractionPayRequest.infractionId.toUpperCase());
    }
    catch (InvalidInfractionIdException e){
      throw new InvalidInfractionIdFormatException("The infraction id format is invalid");
    }
  }

  private LocalTime getTimeOfTheDay(InfractionRequest infractionRequest) {
    try {
      return LocalTime.parse(infractionRequest.timeOfTheDay, DateTimeFormatter.LOCAL_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidInfractionTimeOfTheDayException("The time of the day must be HH:mm");
    }
  }

  private ParkingZone getParkingZone(InfractionRequest infractionRequest) {
    try {
      return ParkingZone.valueOf(infractionRequest.parkingZone.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidInfractionParkingZoneException("Invalid parking zone");
    }
  }

  private PassCode getPassCode(InfractionRequest infractionRequest) {
    if (infractionRequest.passCode.equals("")) {
      throw new EmptyPassCodeException("The pass code is empty");
    }
    try {
      return PassCode.valueOf(infractionRequest.passCode);
    } catch (InvalidPassCodeFormat e) {
      throw new InvalidInfractionPassCodeFormatException("The pass code format is invalid");
    }
  }

  public InfractionResponse toResponse(Infraction infraction) {
    InfractionResponse response = new InfractionResponse();
    if (infraction == null) {
      response.reason = "The pass is Valid";
      return response;
    }
    response.infractionId = infraction.getInfractionId().toString();
    response.amount = infraction.getAmount();
    response.code = infraction.getCode().toString();
    response.reason = infraction.getInfractionDscription();

    return response;
  }
}
