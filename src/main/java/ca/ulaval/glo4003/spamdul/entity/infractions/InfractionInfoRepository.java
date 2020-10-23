package ca.ulaval.glo4003.spamdul.entity.infractions;

public interface InfractionInfoRepository {

  InfractionInfos findBy(InfractionCode infractionCode);
}
