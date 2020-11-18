package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class RechargULUseCase {

  private final RechargULCardRepository rechargULCardRepository;

  public RechargULUseCase(RechargULCardRepository rechargULCardRepository) {
    this.rechargULCardRepository = rechargULCardRepository;
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
}
