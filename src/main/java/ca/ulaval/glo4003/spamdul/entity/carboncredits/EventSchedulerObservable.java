package ca.ulaval.glo4003.spamdul.entity.carboncredits;

import java.util.ArrayList;
import java.util.List;

public class EventSchedulerObservable {

  private List<ScheduleObserver> observers = new ArrayList<>();

  public void register(ScheduleObserver observer) {
      observers.add(observer);
  }

  public void unregister(ScheduleObserver observer) {
    observers.remove(observer);
  }

  protected boolean isBeingObserved() {
    return !observers.isEmpty();
  }

  public void notifyObservers() {

    for (ScheduleObserver observer : observers) {
      observer.listenScheduledEvent();
    }
  }

}
