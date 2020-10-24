package ca.ulaval.glo4003.spamdul.infrastructure.scheduling;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.TransferFundsToCarbonCreditsObservable;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

import java.time.Duration;
import java.util.concurrent.*;

public class EndOfMonthFundsTransfer extends TransferFundsToCarbonCreditsObservable {

  private static EndOfMonthFundsTransfer instance = null;
  private final Calendar calendar;
  private ScheduledExecutorService executorService;
  private ScheduledFuture<?> scheduledFuture;

  private EndOfMonthFundsTransfer(ScheduledExecutorService executorService, Calendar calendar) {
    this.executorService = executorService;
    this.calendar = calendar;
  }

  public static EndOfMonthFundsTransfer getInstance(ScheduledExecutorService executorService,
                                                    Calendar calendar) {
    if (instance == null) {
      instance = new EndOfMonthFundsTransfer(executorService, calendar);
    }
    return instance;
  }

  public static void setInstance(EndOfMonthFundsTransfer instance) {
    EndOfMonthFundsTransfer.instance = instance;
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
