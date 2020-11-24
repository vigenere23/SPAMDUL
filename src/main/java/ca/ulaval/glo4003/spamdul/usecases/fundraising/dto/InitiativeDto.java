package ca.ulaval.glo4003.spamdul.usecases.fundraising.dto;

import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeDto {

  public InitiativeCode code;
  public String name;
  public Amount amount;
}
