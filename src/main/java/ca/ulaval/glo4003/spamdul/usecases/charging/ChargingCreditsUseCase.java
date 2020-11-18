package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class ChargingCreditsUseCase {

  private final RechargULCardRepository rechargULCardRepository;

  public ChargingCreditsUseCase(RechargULCardRepository rechargULCardRepository) {
    this.rechargULCardRepository = rechargULCardRepository;
  }

  public RechargULCard getRechargeULCard(String rechargULCardIdString) {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);
    RechargULCard rechargULCard = rechargULCardRepository.findBy(rechargULCardId);
    return rechargULCard;
  }

  public RechargULCard addCredits(String rechargULCardIdString, double amount) {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);
    RechargULCard rechargULCard = rechargULCardRepository.findBy(rechargULCardId);
    Amount credits = Amount.valueOf(amount);

    rechargULCard.addCredits(credits);

    rechargULCardRepository.update(rechargULCard);
    return rechargULCard;
  }
}
