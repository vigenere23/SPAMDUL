package ca.ulaval.glo4003.spamdul.usecases.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;

public class CarbonCreditsService implements ScheduleObserver {

  private final EventSchedulerObservable eventSchedulerObservable;

  public CarbonCreditsService(EventSchedulerObservable eventSchedulerObservable) {
    this.eventSchedulerObservable = eventSchedulerObservable;
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
    return 238.34;
  }

  @Override
  public void listenScheduledEvent() {
    transferRemainingBudget();
  }
}
