package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;
  private final CarbonCreditsPurchaser carbonCreditsPurchaser;
  private BankRepository bankRepository;
  private TransactionFactory transactionFactory;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable,
                              BankRepository bankRepository,
                              TransactionFactory transactionFactory,
                              CarbonCreditsPurchaser carbonCreditsPurchaser) {
    this.eventSchedulerObservable = eventSchedulerObservable;
    this.carbonCreditsPurchaser = carbonCreditsPurchaser;
    this.bankRepository = bankRepository;
    this.transactionFactory = transactionFactory;
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
    Amount totalAvailableAmount = bankRepository.getSustainableMobilityProjectAccount().getTotalAvailableAmount();

    TransactionDto transactionDto = new TransactionDto();
    transactionDto.transactionType = TransactionType.CARBON_CREDIT;
    transactionDto.amount = totalAvailableAmount.asDouble() * -1;
    Transaction transaction = transactionFactory.create(transactionDto);
    bankRepository.getSustainableMobilityProjectAccount().addTransaction(transaction);

    carbonCreditsPurchaser.purchase(totalAvailableAmount.asDouble());

    return totalAvailableAmount.asDouble();
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
