package ca.ulaval.glo4003.spamdul.infrastructure.db.rechargul;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;

public class RechargULCardRepositoryInMemory implements RechargULCardRepository {

  @Override
  public RechargULCard find(RechargULCardId id) {
    return null;
  }

  @Override
  public void save(RechargULCard rechargULCard) {

  }

  @Override
  public void update(RechargULCard rechargULCard) {

  }
}
