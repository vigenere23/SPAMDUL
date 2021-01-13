package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionInfos {

  private final InfractionType infractionType;
  private final Amount amount;
  private final String description;

  public InfractionInfos(InfractionType infractionType, Amount amount, String description) {
    this.infractionType = infractionType;
    this.amount = amount;
    this.description = description;
  }

  public InfractionType getInfractionType() {
    return infractionType;
  }

  public Amount getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }
}
