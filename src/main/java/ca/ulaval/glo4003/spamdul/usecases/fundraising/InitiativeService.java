package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.bank.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeCode;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.List;

public class InitiativeService {

  private final InitiativeRepository initiativeRepository;
  private final InitiativeFactory initiativeFactory;
  private final BankRepository bankRepository;
  private final TransactionFactory transactionFactory;
  private AccessLevelValidator accessLevelValidator;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeFactory initiativeFactory,
                           BankRepository bankRepository,
                           TransactionFactory transactionFactory,
                           AccessLevelValidator accessLevelValidator) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
    this.bankRepository = bankRepository;
    this.transactionFactory = transactionFactory;
    this.accessLevelValidator = accessLevelValidator;
  }

  public List<Initiative> getAllInitiatives(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    try {
      TransactionDto transactionDto = new TransactionDto();
      transactionDto.transactionType = TransactionType.INITIATIVE;
      transactionDto.amount = initiativeDto.amount * -1;
      Transaction transaction = transactionFactory.create(transactionDto);
      bankRepository.getSustainabilityBankAccount().addTransaction(transaction);

    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmount("Insufficient funds");
    }

    Initiative initiative = initiativeFactory.create(new InitiativeCode(initiativeDto.code),
                                                     initiativeDto.name,
                                                     Amount.valueOf(initiativeDto.amount));
    initiativeRepository.save(initiative);

    return initiative;
  }
}
