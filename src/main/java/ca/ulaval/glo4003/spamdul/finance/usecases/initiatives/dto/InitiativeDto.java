package ca.ulaval.glo4003.spamdul.finance.usecases.initiatives.dto;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InitiativeDto {

  public InitiativeCode code;
  public String name;
  public Amount amount;
}
