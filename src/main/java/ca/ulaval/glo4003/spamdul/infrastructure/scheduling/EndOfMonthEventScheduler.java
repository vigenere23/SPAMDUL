package ca.ulaval.glo4003.spamdul.infrastructure.scheduling;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.ScheduleObserver;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

import java.time.Duration;
import java.util.concurrent.*;

public class EndOfMonthEventScheduler extends EventSchedulerObservable {

  private static EndOfMonthEventScheduler instance = null;
  private static Calendar calendar;
  private static ScheduledExecutorService executorService;
  private ScheduledFuture<?> scheduledFuture;

  private EndOfMonthEventScheduler() { }

  public static EndOfMonthEventScheduler getInstance(ScheduledExecutorService executorService,
                                                     Calendar calendar) {
    if (instance == null) {
      instance = new EndOfMonthEventScheduler();
    }

    setExecutorService(executorService);
    setCalendar(calendar);

    return instance;
  }

  private static void setCalendar(Calendar calendar) {
    EndOfMonthEventScheduler.calendar = calendar;
  }

  private static void setExecutorService(ScheduledExecutorService executorService) {
    EndOfMonthEventScheduler.executorService = executorService;
  }

  public static void setInstance(EndOfMonthEventScheduler instance) {
    EndOfMonthEventScheduler.instance = instance;
  }

  private void launchJob() {
    long initialDelay = Duration.ofDays(1).toNanos() - calendar.now().toLocalTime().toNanoOfDay();
    long scheduleRateInNano = Duration.ofDays(1).toNanos();

    this.scheduledFuture = executorService.scheduleAtFixedRate(
            this::notifyIfFirstOfMonth,
            initialDelay,
            scheduleRateInNano,
            TimeUnit.NANOSECONDS);
  }

  private void stopJob() {
    scheduledFuture.cancel(true);
    executorService.shutdown();
  }

  private void notifyIfFirstOfMonth() {
    if (calendar.now().getDayOfMonth() == 1) {
      notifyObservers();
    }
  }

  @Override
  public void register(ScheduleObserver observer) {
    if (!isBeingObserved()) {
      launchJob();
    }

    super.register(observer);
  }

  @Override
  public void unregister(ScheduleObserver observer) {
    super.unregister(observer);

    if (!isBeingObserved()) {
      stopJob();
    }

  }
}
