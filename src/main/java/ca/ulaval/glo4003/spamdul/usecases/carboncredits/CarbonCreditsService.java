package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.entity.initiatives.Initiative;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.entity.initiatives.InitiativeRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;
  private final CarbonCreditsPurchaser carbonCreditsPurchaser;
  private final BankRepository bankRepository;
  private final TransactionFactory transactionFactory;
  private final InitiativeFactory initiativeFactory;
  private final InitiativeRepository initiativeRepository;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable,
                              BankRepository bankRepository,
                              TransactionFactory transactionFactory,
                              CarbonCreditsPurchaser carbonCreditsPurchaser,
                              InitiativeFactory initiativeFactory,
                              InitiativeRepository initiativeRepository) {
    this.eventSchedulerObservable = eventSchedulerObservable;
    this.carbonCreditsPurchaser = carbonCreditsPurchaser;
    this.bankRepository = bankRepository;
    this.transactionFactory = transactionFactory;
    this.initiativeFactory = initiativeFactory;
    this.initiativeRepository = initiativeRepository;
  }

  public boolean activateAutomaticTransfer(boolean active) {
    if (active) {
      eventSchedulerObservable.register(this);
      return true;
    } else {
      eventSchedulerObservable.unregister(this);
      return false;
    }
  }

  public double transferRemainingBudget() {
    Amount totalAvailableAmount = bankRepository.getSustainabilityBankAccount().getTotalAvailableAmount();
    Initiative initiative = initiativeFactory.create("MCARB",
                                                     "Marché du carbone",
                                                     totalAvailableAmount);

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.transactionType = TransactionType.INITIATIVE;
    transactionDto.amount = totalAvailableAmount.multiply(-1).asDouble();
    Transaction transaction = transactionFactory.create(transactionDto);

    TransactionDto creditsTransactionDto = new TransactionDto();
    creditsTransactionDto.transactionType = TransactionType.CARBON_CREDIT;
    creditsTransactionDto.amount = totalAvailableAmount.asDouble();
    Transaction creditsTransaction = transactionFactory.create(creditsTransactionDto);

    // TODO all this should be a transaction
    bankRepository.getSustainabilityBankAccount().addTransaction(transaction);
    bankRepository.getCarbonCreditsBankAccount().save(creditsTransaction);
    carbonCreditsPurchaser.purchase(CarbonCredits.valueOf(totalAvailableAmount));
    initiativeRepository.save(initiative);

    return totalAvailableAmount.asDouble();
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
