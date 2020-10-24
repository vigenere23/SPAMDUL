package ca.ulaval.glo4003.spamdul.infrastructure.scheduling;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.EventSchedulerObservable;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

import java.time.Duration;
import java.util.concurrent.*;

public class EndOfMonthEventScheduler extends EventSchedulerObservable {

  private static EndOfMonthEventScheduler instance = null;
  private final Calendar calendar;
  private ScheduledExecutorService executorService;
  private ScheduledFuture<?> scheduledFuture;

  private EndOfMonthEventScheduler(ScheduledExecutorService executorService, Calendar calendar) {
    this.executorService = executorService;
    this.calendar = calendar;
  }

  public static EndOfMonthEventScheduler getInstance(ScheduledExecutorService executorService,
                                                     Calendar calendar) {
    if (instance == null) {
      instance = new EndOfMonthEventScheduler(executorService, calendar);
    }
    return instance;
  }

  public static void setInstance(EndOfMonthEventScheduler instance) {
    EndOfMonthEventScheduler.instance = instance;
  }

  public void launchJob() {
    long initialDelay = Duration.ofDays(1).toNanos() - calendar.now().toLocalTime().toNanoOfDay();
    long scheduleRateInNano = Duration.ofDays(1).toNanos();

    this.scheduledFuture = executorService.scheduleAtFixedRate(
            this::notifyIfFirstOfMonth,
            initialDelay,
            scheduleRateInNano,
            TimeUnit.NANOSECONDS);
  }

  public void stopJob() {
    scheduledFuture.cancel(true);
    executorService.shutdown();
  }

  private void notifyIfFirstOfMonth() {
    if (calendar.now().getDayOfMonth() == 1) {
      notifyObservers();
    }
  }

}
