package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCreator;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import java.util.ArrayList;
import java.util.List;

public class InitiativeUseCase {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeCreator initiativeCreator;
  private final AccessLevelValidator accessLevelValidator;
  private final InitiativeDtoAssembler initiativeDtoAssembler;

  public InitiativeUseCase(InitiativeRepository initiativeRepository,
                           InitiativeCreator initiativeCreator,
                           AccessLevelValidator accessLevelValidator,
                           InitiativeDtoAssembler initiativeDtoAssembler) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeCreator = initiativeCreator;
    this.accessLevelValidator = accessLevelValidator;
    this.initiativeDtoAssembler = initiativeDtoAssembler;
  }

  public List<InitiativeDto> getAllInitiatives(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);
    List<InitiativeDto> dtos = new ArrayList<>();

    for (Initiative initiative : initiativeRepository.findAll()) {
      dtos.add(initiativeDtoAssembler.toDto(initiative));
    }

    return dtos;
  }

  public InitiativeDto addInitiative(InitiativeDto initiativeDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Initiative initiative = initiativeCreator.createInitiative(initiativeDto.name,
                                                               initiativeDto.amount);
    initiativeRepository.save(initiative);

    return initiativeDtoAssembler.toDto(initiative);
  }
}
