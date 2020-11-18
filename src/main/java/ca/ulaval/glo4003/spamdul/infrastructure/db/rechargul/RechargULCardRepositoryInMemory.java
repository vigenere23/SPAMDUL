package ca.ulaval.glo4003.spamdul.infrastructure.db.rechargul;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardAlreadyExistsException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class RechargULCardRepositoryInMemory implements RechargULCardRepository {

  private final Map<RechargULCardId, RechargULCard> rechargULCards = new HashMap<>();

  @Override
  public RechargULCard findBy(RechargULCardId id) {
    if (!rechargULCards.containsKey(id)) {
      throw new RechargULCardNotFoundException();
    }

    return rechargULCards.get(id);
  }

  @Override
  public void save(RechargULCard rechargULCard) {
    if (rechargULCards.containsKey(rechargULCard.getId())) {
      throw new RechargULCardAlreadyExistsException();
    }

    rechargULCards.put(rechargULCard.getId(), rechargULCard);
  }

  @Override
  public void update(RechargULCard rechargULCard) {
    if (!rechargULCards.containsKey(rechargULCard.getId())) {
      throw new RechargULCardNotFoundException();
    }

    rechargULCards.put(rechargULCard.getId(), rechargULCard);
  }
}
