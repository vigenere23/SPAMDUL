package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionCreator {

  private final InfractionTypeFactory infractionTypeFactory;
  private final InfractionFactory infractionFactory;
  private final InfractionAmountRepository infractionAmountRepository;

  public InfractionCreator(InfractionTypeFactory infractionTypeFactory,
                           InfractionFactory infractionFactory,
                           InfractionAmountRepository infractionAmountRepository) {
    this.infractionTypeFactory = infractionTypeFactory;
    this.infractionFactory = infractionFactory;
    this.infractionAmountRepository = infractionAmountRepository;
  }

  public Infraction createInfraction(InvalidAccessException exception) {
    InfractionType infractionType = infractionTypeFactory.create(exception);
    Amount amount = infractionAmountRepository.findBy(infractionType);
    return infractionFactory.create(infractionType, amount);
  }
}
