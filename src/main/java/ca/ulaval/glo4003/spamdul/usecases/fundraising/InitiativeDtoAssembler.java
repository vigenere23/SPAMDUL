package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;

public class InitiativeDtoAssembler {

  public InitiativeDto toDto(Initiative initiative) {
    InitiativeDto dto = new InitiativeDto();
    dto.code = initiative.getCode();
    dto.amount = initiative.getAmount();
    dto.name = initiative.getName();

    return dto;
  }

}
