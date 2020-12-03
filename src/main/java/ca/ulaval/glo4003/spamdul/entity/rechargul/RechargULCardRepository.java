package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.user.User;

public interface RechargULCardRepository {

  User findBy(RechargULCardId id);

  void save(RechargULCard rechargULCard);

  void update(RechargULCard rechargULCard);
}
