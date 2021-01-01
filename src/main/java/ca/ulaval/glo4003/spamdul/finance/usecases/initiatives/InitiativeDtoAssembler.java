package ca.ulaval.glo4003.spamdul.finance.usecases.initiatives;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.finance.usecases.initiatives.dto.InitiativeDto;

public class InitiativeDtoAssembler {

  public InitiativeDto toDto(Initiative initiative) {
    InitiativeDto dto = new InitiativeDto();
    dto.code = initiative.getCode();
    dto.amount = initiative.getAmount();
    dto.name = initiative.getName();

    return dto;
  }

}
