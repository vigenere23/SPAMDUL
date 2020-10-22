package ca.ulaval.glo4003.spamdul.context.carboncredits;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarbonCreditsTask implements Runnable {

  private ScheduledExecutorService executor;

  public CarbonCreditsTask(ScheduledExecutorService executor) {
    this.executor = executor;
    setNextTask();
  }

  @Override
  public void run() {

    setNextTask();
  }

  private void setNextTask() {
    ZonedDateTime startOfMonth = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
    long delay = startOfMonth.until(startOfMonth.plusMonths(1), ChronoUnit.MILLIS);
    executor.schedule(this, delay, TimeUnit.MILLISECONDS);
  }

  private void executeTask() {

  }
}
