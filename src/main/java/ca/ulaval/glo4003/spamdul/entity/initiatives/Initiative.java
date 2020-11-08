package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.utils.Amount;

public class Initiative {

  private final InitiativeId id;
  private final String code;
  private final String name;
  private final Amount amount;


  public Initiative(InitiativeId id, String code, String name, Amount amount) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.amount = amount;
  }

  public InitiativeId getId() {
    return id;
  }
  
  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public Amount getAmount() {
    return amount;
  }
}
