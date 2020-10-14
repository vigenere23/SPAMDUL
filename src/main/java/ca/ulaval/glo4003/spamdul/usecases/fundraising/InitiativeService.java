package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;

  public InitiativeService(InitiativeRepository initiativeRepository) {
    this.initiativeRepository = initiativeRepository;
  }

  public List<Initiative> getAllInitiatives() {
    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(Initiative initiative) {
    // TODO check if budget allows it
    // TODO create and save transaction
    // TODO transfer money
    initiativeRepository.save(initiative);
    return initiative;
  }
}
