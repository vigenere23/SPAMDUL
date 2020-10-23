package ca.ulaval.glo4003.spamdul.entity.infractions;

public interface InfractionRepository {

  InfractionId save(Infraction infraction);

  Infraction findBy(InfractionId infractionId);

}
