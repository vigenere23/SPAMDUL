package ca.ulaval.glo4003.spamdul.finance.context.initiatives;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

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
