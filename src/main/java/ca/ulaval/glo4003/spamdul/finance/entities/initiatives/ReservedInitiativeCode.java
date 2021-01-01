package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ReservedInitiativeCode {
  CARBON_CREDITS(InitiativeCode.valueOf("MCARB"));

  private final InitiativeCode value;

  ReservedInitiativeCode(InitiativeCode value) {
    this.value = value;
  }

  public InitiativeCode getValue() {
    return value;
  }

  public static Set<InitiativeCode> getValues() {
    return Arrays.stream(values()).map(ReservedInitiativeCode::getValue).collect(Collectors.toSet());
  }
}
