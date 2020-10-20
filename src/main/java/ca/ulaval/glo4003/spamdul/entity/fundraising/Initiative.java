package ca.ulaval.glo4003.spamdul.entity.fundraising;

import ca.ulaval.glo4003.spamdul.utils.Amount;

public class Initiative {

  private final InitiativeId id;
  private final String name;
  private final Amount amount;


  public Initiative(InitiativeId id, String name, Amount amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }

  public InitiativeId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Amount getAmount() {
    return amount;
  }
}
