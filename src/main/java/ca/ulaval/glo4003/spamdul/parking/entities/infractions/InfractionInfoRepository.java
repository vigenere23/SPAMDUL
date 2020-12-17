package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

public interface InfractionInfoRepository {

  InfractionInfosDto findBy(InfractionCode infractionCode);
}
