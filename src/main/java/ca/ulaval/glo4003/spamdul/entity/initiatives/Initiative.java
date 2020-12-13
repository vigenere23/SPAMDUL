package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.shared.amount.Amount;

public class Initiative {

  private final InitiativeId id;
  private final InitiativeCode code;
  private final String name;
  private final Amount amount;


  public Initiative(InitiativeId id, InitiativeCode code, String name, Amount amount) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.amount = amount;
  }

  public InitiativeId getId() {
    return id;
  }

  public InitiativeCode getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public Amount getAmount() {
    return amount;
  }
}
