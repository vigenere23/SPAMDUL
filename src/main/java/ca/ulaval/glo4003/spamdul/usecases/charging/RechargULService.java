package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class RechargULService {

  private final RechargULCardRepository rechargULCardRepository;
  private final RechargULCardFactory rechargULCardFactory;

  public RechargULService(RechargULCardRepository rechargULCardRepository,
                          RechargULCardFactory rechargULCardFactory) {
    this.rechargULCardRepository = rechargULCardRepository;
    this.rechargULCardFactory = rechargULCardFactory;
  }

  public RechargULCard getRechargULCard(RechargULCardId rechargULCardId) {
    return rechargULCardRepository.findBy(rechargULCardId);
  }

  public RechargULCard addCredits(RechargULCardId rechargULCardId, Amount amount) {
    RechargULCard rechargULCard = rechargULCardRepository.findBy(rechargULCardId);

    rechargULCard.addCredits(amount);

    rechargULCardRepository.update(rechargULCard);
    return rechargULCard;
  }

  public RechargULCard createCard() {
    RechargULCard card = rechargULCardFactory.create();
    rechargULCardRepository.save(card);
    return card;
  }
}