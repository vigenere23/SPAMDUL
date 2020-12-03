package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.user.User;

public interface InfractionRepository {

  InfractionId save(Infraction infraction);

  User findBy(InfractionId infractionId);
}
