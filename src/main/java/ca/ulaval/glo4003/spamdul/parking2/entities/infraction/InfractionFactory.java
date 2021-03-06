package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionFactory {

  private final InfractionIdFactory infractionIdFactory;

  public InfractionFactory(InfractionIdFactory infractionIdFactory) {
    this.infractionIdFactory = infractionIdFactory;
  }

  public Infraction create(InfractionType infractionType, Amount amount) {
    return new Infraction(infractionIdFactory.create(), infractionType, amount);
  }
}
