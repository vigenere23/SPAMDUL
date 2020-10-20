package ca.ulaval.glo4003.spamdul.entity.infractions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class TimePeriodValidatorTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final PassCode A_PASS_CODE = new PassCode();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);
  private static final LocalDateTime ANOTHER_START_DATE_TIME = LocalDateTime.of(2019, 1, 1, 0, 0);
  private static final LocalDateTime ANOTHER_END_DATE_TIME = LocalDateTime.of(3000, 1, 1, 0, 0);
  private static final TimePeriod ANOTHER_TIME_PERIOD = new TimePeriod(ANOTHER_START_DATE_TIME,
                                                                       ANOTHER_END_DATE_TIME,
                                                                       TimePeriodDayOfWeek.ALL);
  private static final LocalTime A_TIME = LocalTime.now();
  private static final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  private static final Pass ANOTHER_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, ANOTHER_TIME_PERIOD);

  private PassValidator nextValidator;
  private TimePeriodValidator timePeriodValidator;

  @Before
  public void setUp() {
    nextValidator = mock(PassValidator.class);
    timePeriodValidator = new TimePeriodValidator();
  }

  @Test
  public void givenNextValidator_whenTimePeriodIsValid_thenShouldCallNextValidatorToValidate() {
    timePeriodValidator.setNextValidator(nextValidator);

    timePeriodValidator.validate(ANOTHER_PASS, A_PARKING_ZONE, A_TIME);

    verify(nextValidator).validate(ANOTHER_PASS, A_PARKING_ZONE, A_TIME);
  }

  @Test(expected = InfractionException.class)
  public void whenTimePeriodIsInvalid_thenShouldThrowInfractionException() {

    timePeriodValidator.validate(A_PASS, A_PARKING_ZONE, A_TIME);
  }
}
