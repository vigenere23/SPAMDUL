package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.usecases.banking.AccountService;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;
  private final AccountService accountService;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeFactory initiativeFactory,
                           AccountService accountService) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
    this.accountService = accountService;
  }

  public List<Initiative> getAllInitiatives() {
    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto) {
    // TODO check if budget allows it
    // TODO create and save transaction
    // TODO transfer money
    accountService.fundInitiative(Amount.valueOf(initiativeDto.amount));
    Initiative initiative = initiativeFactory.create(initiativeDto.name, Amount.valueOf(initiativeDto.amount));
    initiativeRepository.save(initiative);
    return initiative;
  }
}
