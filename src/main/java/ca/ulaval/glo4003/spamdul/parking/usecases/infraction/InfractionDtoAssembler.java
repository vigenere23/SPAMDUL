package ca.ulaval.glo4003.spamdul.parking.usecases.infraction;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.dto.InfractionDto;

public class InfractionDtoAssembler {

  public InfractionDto toDto(Infraction infraction) {
    if (infraction == null) {
      return null;
    }

    InfractionDto infractionDto = new InfractionDto();
    infractionDto.id = infraction.getInfractionId();
    infractionDto.amount = infraction.getAmount();
    infractionDto.code = infraction.getCode();
    infractionDto.infractionDescription = infraction.getInfractionDescription();

    return infractionDto;
  }
}
