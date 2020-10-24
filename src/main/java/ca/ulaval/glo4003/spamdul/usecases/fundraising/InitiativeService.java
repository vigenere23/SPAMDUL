package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;
  private final Account sustainableMobilityProjectAccount;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeFactory initiativeFactory,
                           Account sustainableMobilityProjectAccount) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
    this.sustainableMobilityProjectAccount = sustainableMobilityProjectAccount;
  }

  public List<Initiative> getAllInitiatives() {
    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto) {
    // TODO create and save transaction
    // TODO transfer money
    try {
      sustainableMobilityProjectAccount.withdrawFunds(Amount.valueOf(initiativeDto.amount));
    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmount("Insufficient funds");
    }
    Initiative initiative = initiativeFactory.create(initiativeDto.name, Amount.valueOf(initiativeDto.amount));
    initiativeRepository.save(initiative);
    return initiative;
  }
}
