package ca.ulaval.glo4003.spamdul.usecases.fundraising;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
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
  private BankRepository bankRepository;
  private TransactionFactory transactionFactory;

  public InitiativeService(InitiativeRepository initiativeRepository,
                           InitiativeFactory initiativeFactory,
                           BankRepository bankRepository,
                           TransactionFactory transactionFactory) {
    this.initiativeRepository = initiativeRepository;
    this.initiativeFactory = initiativeFactory;
    this.bankRepository = bankRepository;
    this.transactionFactory = transactionFactory;
  }

  public List<Initiative> getAllInitiatives() {
    return initiativeRepository.findAll();
  }

  public Initiative addInitiative(InitiativeDto initiativeDto) {
    try {
      TransactionDto transactionDto = new TransactionDto();
      transactionDto.transactionType = TransactionType.INITIATIVE;
      transactionDto.amount = initiativeDto.amount * -1;
      Transaction transaction = transactionFactory.create(transactionDto);
      bankRepository.getSustainableMobilityProjectAccount().addTransaction(transaction);
      //TODO a tester

    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmount("Insufficient funds");
    }

    Initiative initiative = initiativeFactory.create(initiativeDto.name, Amount.valueOf(initiativeDto.amount));
    initiativeRepository.save(initiative);
    return initiative;
  }
}
