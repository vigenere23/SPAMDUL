package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

public interface InfractionInfosRepository {

  InfractionInfos findBy(InfractionType infractionType);

  void save(InfractionInfos infractionInfos);
}
