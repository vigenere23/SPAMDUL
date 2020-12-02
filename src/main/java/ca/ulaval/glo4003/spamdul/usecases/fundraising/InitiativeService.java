package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeCreator initiativeCreator;
  private final AccessLevelValidator accessLevelValidator;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeCreator initiativeCreator,
                           AccessLevelValidator accessLevelValidator) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeCreator = initiativeCreator;
    this.accessLevelValidator = accessLevelValidator;
  }

  public List<Initiative> getAllInitiatives(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Initiative initiative = initiativeCreator.createInitiative(initiativeDto.name,
                                                               initiativeDto.amount);
    initiativeRepository.save(initiative);

    return initiative;
  }
}
