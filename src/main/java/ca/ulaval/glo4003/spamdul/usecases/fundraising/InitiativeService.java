package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativesBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;
  private final InitiativesBankAccount initiativesBankAccount;
  private final AccessLevelValidator accessLevelValidator;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeFactory initiativeFactory,
                           InitiativesBankAccount initiativesBankAccount,
                           AccessLevelValidator accessLevelValidator) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
    this.initiativesBankAccount = initiativesBankAccount;
    this.accessLevelValidator = accessLevelValidator;
  }

  public List<Initiative> getAllInitiatives(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    try {
      initiativesBankAccount.addExpense(initiativeDto.amount);
    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmount("Insufficient funds");
    }

    Initiative initiative = initiativeFactory.create(initiativeDto.code,
                                                     initiativeDto.name,
                                                     initiativeDto.amount);
    initiativeRepository.save(initiative);

    return initiative;
  }
}
