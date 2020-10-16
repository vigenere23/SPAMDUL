package ca.ulaval.glo4003.spamdul.entity.fundraising;

public class Initiative {

  private final InitiativeId id;
  private final String name;
  private final double amount;


  public Initiative(InitiativeId id, String name, double amount) {
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

  public double getAmount() {
    return amount;
  }
}
