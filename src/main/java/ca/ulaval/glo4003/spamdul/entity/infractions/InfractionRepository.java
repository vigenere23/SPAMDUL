package ca.ulaval.glo4003.spamdul.entity.infractions;

public interface InfractionRepository {

  Infraction findBy(InfractionCode infractionCode);
}
