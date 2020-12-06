package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class InitiativeId extends Id {

  private InitiativeId(String value) {
    super(value);
  }

  public static InitiativeId valueOf(String initiativeId) {
    return new InitiativeId(initiativeId);
  }
}
