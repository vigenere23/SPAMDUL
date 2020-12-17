package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class InitiativeId extends Id {

  private InitiativeId(String value) {
    super(value);
  }

  public static InitiativeId valueOf(String initiativeId) {
    return new InitiativeId(initiativeId);
  }
}
