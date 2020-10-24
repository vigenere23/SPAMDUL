package ca.ulaval.glo4003.spamdul.infrastructure.scheduling;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.TransferFundsToCarbonCreditsObserver;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EndOfMonthFundsTransferTest {
  public static final long FIVE_MILLIS = 1;
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(2019, 1, 1, 12, 0, 0, 0);
  public static final LocalDateTime A_DATE_ONE_NANO_BEFORE_NEXT_DAY = LocalDateTime.of(2019, 2, 1, 23, 59, 59, 999999999);
  public static final LocalDateTime A_FIRST_OF_THE_MONTH = LocalDateTime.of(2019, 1, 1, 0, 0, 0, 0);
  public static final LocalDateTime NOT_FIRST_OF_MONTH = LocalDateTime.of(1000, 2, 4, 0, 0);

  @Mock
  private TransferFundsToCarbonCreditsObserver transferFundsToCarbonCreditsObserver;
  @Mock
  private Calendar calendar;
  private ScheduledExecutorService executorService;

  private EndOfMonthFundsTransfer endOfMonthFundsTransfer;

  @After
  public void stopThread() {
    EndOfMonthFundsTransfer.setInstance(null);
  }

  @Test
  public void whenLaunchJob_shouldCallCalendarNowToComputeDelayUntilNextDay() {
    executorService = mock(ScheduledExecutorService.class);
    endOfMonthFundsTransfer = EndOfMonthFundsTransfer.getInstance(executorService, calendar);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);

    endOfMonthFundsTransfer.launchJob();

    verify(calendar, times(1)).now();
  }

  @Test
  public void whenLaunchJob_shouldCallExecutorServiceWithRightParameters() {
    executorService = mock(ScheduledExecutorService.class);
    endOfMonthFundsTransfer = EndOfMonthFundsTransfer.getInstance(executorService, calendar);
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY);

    endOfMonthFundsTransfer.launchJob();

    verify(executorService).scheduleAtFixedRate(
            any(Runnable.class),
            eq(FIVE_MILLIS),
            eq(Duration.ofDays(1).toNanos()),
            eq(TimeUnit.NANOSECONDS));
  }

  // If this fails: it's because your computer is slow, it is testing a call made in a concurrency condition.
  // Add time to the Thread.sleep and it should work
  @Test
  public void givenFirstOfMonth_whenLaunchJob_shouldNotifyObserver() throws InterruptedException {
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY, A_FIRST_OF_THE_MONTH);
    executorService = Executors.newSingleThreadScheduledExecutor();
    endOfMonthFundsTransfer = EndOfMonthFundsTransfer.getInstance(executorService, calendar);
    endOfMonthFundsTransfer.register(transferFundsToCarbonCreditsObserver);

    endOfMonthFundsTransfer.launchJob();

    Thread.sleep(FIVE_MILLIS);

    endOfMonthFundsTransfer.stopJob();

    verify(transferFundsToCarbonCreditsObserver).transferRemainingFundsToCarbonCredits();
  }

  @Test
  public void givenNotFirstOfMonth_whenLaunchJob_shouldNotNotifyObserver() throws InterruptedException {
    when(calendar.now()).thenReturn(A_DATE_ONE_NANO_BEFORE_NEXT_DAY, NOT_FIRST_OF_MONTH);
    executorService = Executors.newSingleThreadScheduledExecutor();
    endOfMonthFundsTransfer = EndOfMonthFundsTransfer.getInstance(executorService, calendar);
    endOfMonthFundsTransfer.register(transferFundsToCarbonCreditsObserver);

    endOfMonthFundsTransfer.launchJob();

    Thread.sleep(FIVE_MILLIS);

    endOfMonthFundsTransfer.stopJob();

    verify(transferFundsToCarbonCreditsObserver, never()).transferRemainingFundsToCarbonCredits();
  }
}
