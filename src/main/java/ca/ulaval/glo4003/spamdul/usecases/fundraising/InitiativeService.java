package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeFactory initiativeFactory) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
  }

  public List<Initiative> getAllInitiatives() {
    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto) {
    // TODO check if budget allows it
    // TODO create and save transaction
    // TODO transfer money
    Initiative initiative = initiativeFactory.create(initiativeDto.name, initiativeDto.amount);
    initiativeRepository.save(initiative);
    return initiative;
  }
}
