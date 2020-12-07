package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class InitiativeCode extends Id {

  private InitiativeCode(String value) {
    super(value);
  }

  public static InitiativeCode valueOf(String code) {
    return new InitiativeCode(code);
  }

  public static InitiativeCode valueOf(ReservedInitiativeCode code) {
    return new InitiativeCode(code.getValue().toString());
  }
}
