package ca.ulaval.glo4003.spamdul.entity.carboncredits;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventSchedulerObservableTest {

  @Mock
  private ScheduleObserver scheduleObserver;
  private EventSchedulerObservable eventSchedulerObservable = new EventSchedulerObservable();

  @Test
  public void givenObserverRegisteredOnce_whenNotify_shouldNotifyOnce() {
    eventSchedulerObservable.register(scheduleObserver);

    eventSchedulerObservable.notifyObservers();

    verify(scheduleObserver, times(1)).listenScheduledEvent();
  }

  @Test
  public void givenObserverRegisteredMoreThanOnce_whenNotify_shouldNotifyOnce() {
    eventSchedulerObservable.register(scheduleObserver);
    eventSchedulerObservable.register(scheduleObserver);

    eventSchedulerObservable.notifyObservers();

    verify(scheduleObserver, times(1)).listenScheduledEvent();
  }

  @Test
  public void givenObserverUnregistered_whenNotify_shouldNotNotify() {
    eventSchedulerObservable.register(scheduleObserver);
    eventSchedulerObservable.unregister(scheduleObserver);

    eventSchedulerObservable.notifyObservers();

    verify(scheduleObserver, never()).listenScheduledEvent();
  }

}
