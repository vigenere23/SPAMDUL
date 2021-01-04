package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class Infraction {

  private final InfractionId infractionId;
  private final InfractionType infractionType;
  private final Amount amount;

  public Infraction(InfractionId infractionId,
                    InfractionType infractionType, Amount amount) {
    this.infractionId = infractionId;
    this.infractionType = infractionType;
    this.amount = amount;
  }

  public InfractionId getId() {
    return infractionId;
  }
}
