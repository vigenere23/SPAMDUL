package ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits;

import java.util.HashSet;
import java.util.Set;

public class EventSchedulerObservable {

  private final Set<ScheduleObserver> observers = new HashSet<>();

  public void register(ScheduleObserver observer) {
    observers.add(observer);
  }

  public void unregister(ScheduleObserver observer) {
    observers.remove(observer);
  }

  protected boolean isNotBeingObserved() {
    return observers.isEmpty();
  }

  public void notifyObservers() {

    for (ScheduleObserver observer : observers) {
      observer.listenScheduledEvent();
    }
  }
}
