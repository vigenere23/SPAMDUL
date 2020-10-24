package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable) {
    this.eventSchedulerObservable = eventSchedulerObservable;
  }

  public void activateAutomaticTransfer(boolean active) {
    if (active) {
      eventSchedulerObservable.register(this);
    } else {
      eventSchedulerObservable.unregister(this);
    }
  }

  // TODO:
  public double transferRemainingBudget() {

    return 238.34;
  }

  public double getTotalCarbonCredits() {
    // TODO add logic to get total of transferred carbon credits
    // TODO maybe from TransactionRepository or CarbonCreditBankAccount
    // TODO
    return 1233.34;
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
