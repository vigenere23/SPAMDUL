package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;

public class InitiativeId extends LongId {

  private InitiativeId(long value) {
    super(value);
  }

  public static InitiativeId valueOf(String initiativeId) {
    return new InitiativeId(Long.parseLong(initiativeId));
  }
}
