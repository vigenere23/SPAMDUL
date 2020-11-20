package ca.ulaval.glo4003.spamdul.infrastructure.scheduling;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EndOfMonthEventSchedulerTest {

  public static final long ONE_NANO = 1;
  public static final int THREAD_SLEEP_TIME_IN_MILLIS = 15;
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(2019, 1, 1, 12, 0, 0, 0);
  public static final LocalDateTime A_DATE_ONE_NANO_BEFORE_NEXT_DAY = LocalDateTime.of(2019,
                                                                                       2,
                                                                                       1,
                                                                                       23,
                                                                                       59,
                                                                                       59,
                                                                                       999999999);
  public static final LocalDateTime A_FIRST_OF_THE_MONTH = LocalDateTime.of(2019, 1, 1, 0, 0, 0, 0);
  public static final LocalDateTime NOT_FIRST_OF_MONTH = LocalDateTime.of(1000, 2, 4, 0, 0);

  @Mock
  private ScheduleObserver scheduleObserver;
  @Mock
  private Calendar calendar;
  private ScheduledExecutorService executorService;

  private EndOfMonthEventScheduler endOfMonthEventScheduler;

  @After
  public void stopThread() {
    EndOfMonthEventScheduler.setInstance(null);
  }

  @Test
  public void whenBeingObserved_shouldCallCalendarNowToComputeDelayUntilNextDay() {
    executorService = mock(ScheduledExecutorService.class);
    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(executorService, calendar);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);

    endOfMonthEventScheduler.register(scheduleObserver);

    verify(calendar, times(1)).now();
  }

  @Test
  public void whenBeingObserved_shouldCallExecutorServiceWithRightParameters() {
    executorService = mock(ScheduledExecutorService.class);
    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(executorService, calendar);
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY);

    endOfMonthEventScheduler.register(scheduleObserver);

    verify(executorService).scheduleAtFixedRate(
        any(Runnable.class),
        eq(ONE_NANO),
        eq(Duration.ofDays(1).toNanos()),
        eq(TimeUnit.NANOSECONDS));
  }

  // If this fails: it's because your computer is slow, it is testing a call made in a concurrency condition.
  // Add time to the Thread.sleep and it should work
  @Test
  public void givenFirstOfMonth_whenBeingObserved_shouldNotifyObserver() throws InterruptedException {
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY, A_FIRST_OF_THE_MONTH);
    executorService = Executors.newSingleThreadScheduledExecutor();
    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(executorService, calendar);

    endOfMonthEventScheduler.register(scheduleObserver);

    Thread.sleep(THREAD_SLEEP_TIME_IN_MILLIS);
    endOfMonthEventScheduler.unregister(scheduleObserver);

    verify(scheduleObserver).listenScheduledEvent();
  }

  @Test
  public void whenNotBeingObserved_shouldNotNotifyOnFirstOfMonth() throws InterruptedException {
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY, A_FIRST_OF_THE_MONTH);

    executorService = Executors.newSingleThreadScheduledExecutor();
    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(executorService, calendar);
    endOfMonthEventScheduler.register(scheduleObserver);

    endOfMonthEventScheduler.unregister(scheduleObserver);

    Thread.sleep(THREAD_SLEEP_TIME_IN_MILLIS);
    endOfMonthEventScheduler.unregister(scheduleObserver);

    verify(scheduleObserver, never()).listenScheduledEvent();
  }

  @Test
  public void givenNotFirstOfMonth_whenBeingObserved_shouldNotNotifyObserver() throws InterruptedException {
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY, NOT_FIRST_OF_MONTH);
    executorService = Executors.newSingleThreadScheduledExecutor();
    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(executorService, calendar);

    endOfMonthEventScheduler.register(scheduleObserver);

    Thread.sleep(THREAD_SLEEP_TIME_IN_MILLIS);
    endOfMonthEventScheduler.unregister(scheduleObserver);

    verify(scheduleObserver, never()).listenScheduledEvent();
  }

  @Test
  public void givenBeingObserved_whenNotBeingObserved_shouldStopJob() {
    ScheduledFuture future = mock(ScheduledFuture.class);
    executorService = mock(ScheduledExecutorService.class);

    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY, NOT_FIRST_OF_MONTH);
    when(executorService.scheduleAtFixedRate(
        any(Runnable.class),
        eq(ONE_NANO),
        eq(Duration.ofDays(1).toNanos()),
        eq(TimeUnit.NANOSECONDS))).thenReturn(future);

    endOfMonthEventScheduler = EndOfMonthEventScheduler.getInstance(executorService, calendar);

    endOfMonthEventScheduler.register(scheduleObserver);
    endOfMonthEventScheduler.unregister(scheduleObserver);

    verify(future).cancel(true);
  }
}
