package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativePopulator implements Populator {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;

  public InitiativePopulator(InitiativeRepository initiativeRepository,
                             InitiativeFactory initiativeFactory) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
  }

  @Override public void populate(int numberOfRecords) {
    for (int recordNumber = 0; recordNumber < numberOfRecords; recordNumber++) {
      Initiative initiative = initiativeFactory.create("SOME_NAME", Amount.valueOf(123.34));
      initiativeRepository.save(initiative);
    }
  }
}
