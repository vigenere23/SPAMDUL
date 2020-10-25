package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;
  private final CarbonCreditsPurchaser carbonCreditsPurchaser;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable,
                              CarbonCreditsPurchaser carbonCreditsPurchaser) {
    this.eventSchedulerObservable = eventSchedulerObservable;
    this.carbonCreditsPurchaser = carbonCreditsPurchaser;
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
    // TODO:
    double amount = 238.34;
    carbonCreditsPurchaser.purchase(amount);
    return amount;
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
