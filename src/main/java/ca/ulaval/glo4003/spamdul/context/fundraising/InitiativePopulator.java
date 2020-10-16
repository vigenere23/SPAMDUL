package ca.ulaval.glo4003.spamdul.context.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;

public class InitiativePopulator {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;

  public InitiativePopulator(InitiativeRepository initiativeRepository,
                             InitiativeFactory initiativeFactory) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
  }

  public void populate(int numberOfRecords) {
    for (int recordNumber = 0; recordNumber < numberOfRecords; recordNumber++) {
      Initiative initiative = initiativeFactory.create("SOME_NAME", 123.34);
      initiativeRepository.save(initiative);
    }
  }
}
