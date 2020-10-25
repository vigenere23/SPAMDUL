package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;
  private BankRepository bankRepository;
  private TransactionFactory transactionFactory;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable,
                              BankRepository bankRepository,
                              TransactionFactory transactionFactory) {
    this.eventSchedulerObservable = eventSchedulerObservable;
    this.bankRepository = bankRepository;
    this.transactionFactory = transactionFactory;
  }

  public void activateAutomaticTransfer(boolean active) {
    if (active) {
      eventSchedulerObservable.register(this);
    } else {
      eventSchedulerObservable.unregister(this);
    }
  }


  public double transferRemainingBudget() {
    MainBankAccount mainBankAccount = bankRepository.getMainBankAccount();
    Amount totalAvailableAmount = mainBankAccount.getTotalInitiativesBudget();

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.transactionType = TransactionType.CARBON_CREDIT;
    transactionDto.amount = totalAvailableAmount.asDouble();
    Transaction transaction = transactionFactory.create(transactionDto);
    mainBankAccount.addTransaction(transaction); //TODO a tester

    return totalAvailableAmount.asDouble();
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
