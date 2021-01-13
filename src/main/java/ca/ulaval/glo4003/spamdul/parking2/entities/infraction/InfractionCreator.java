package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;

public class InfractionCreator {

  private final InfractionTypeFactory infractionTypeFactory;
  private final InfractionFactory infractionFactory;
  private final InfractionInfosRepository infractionInfosRepository;

  public InfractionCreator(InfractionTypeFactory infractionTypeFactory,
                           InfractionFactory infractionFactory,
                           InfractionInfosRepository infractionInfosRepository) {
    this.infractionTypeFactory = infractionTypeFactory;
    this.infractionFactory = infractionFactory;
    this.infractionInfosRepository = infractionInfosRepository;
  }

  public Infraction createInfraction(InvalidAccessException exception) {
    InfractionType infractionType = infractionTypeFactory.create(exception);
    InfractionInfos infractionInfos = infractionInfosRepository.findBy(infractionType);
    return infractionFactory.create(infractionType, infractionInfos.getAmount());
  }
}
