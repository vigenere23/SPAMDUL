package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardFactory;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;

public class RechargULCardPopulator implements Populator {

  private final RechargULCardFactory rechargULCardFactory;
  private final RechargULCardRepository rechargULCardRepository;

  public RechargULCardPopulator(RechargULCardFactory rechargULCardFactory,
                                RechargULCardRepository rechargULCardRepository) {
    this.rechargULCardFactory = rechargULCardFactory;
    this.rechargULCardRepository = rechargULCardRepository;
  }

  @Override public void populate(int numberOfRecords) {
    for (int cardNumber = 0; cardNumber < numberOfRecords; cardNumber++) {
      RechargULCard card = rechargULCardFactory.create();
      rechargULCardRepository.save(card);
    }
  }
}
