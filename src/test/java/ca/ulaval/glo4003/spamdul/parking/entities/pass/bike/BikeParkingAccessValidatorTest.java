package ca.ulaval.glo4003.spamdul.parking.entities.pass.bike;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BikeParkingAccessValidatorTest {

  public static final BikeParkingPassCode BIKE_PARKING_PASS_CODE = BikeParkingPassCode.valueOf("1234");
  private BikeParkingAccessValidator bikeParkingAccessValidator;

  @Mock
  Calendar calendar;
  @Mock
  TimePeriod timePeriod;

  @Before
  public void setUp() throws Exception {
    when(calendar.now()).thenReturn(LocalDateTime.now());
    bikeParkingAccessValidator = new BikeParkingAccessValidator(calendar);
  }

  @Test
  public void whenValidating_shouldValidateIfTimePeriodBoundCurrentTime() {
    bikeParkingAccessValidator.validate(new BikeParkingPass(BIKE_PARKING_PASS_CODE, timePeriod));

    verify(timePeriod, times(1)).bounds(calendar.now());
  }

  @Test
  public void givenAValidBikeParkingAccess_whenValidating_shouldReturnValidAccess() {
    when(timePeriod.bounds(calendar.now())).thenReturn(true);

    boolean accessGranted = bikeParkingAccessValidator.validate(new BikeParkingPass(BIKE_PARKING_PASS_CODE,
                                                                                    timePeriod));

    assertThat(accessGranted).isTrue();
  }

  @Test
  public void givenAnInvalidBikeParkingAccess_whenValidating_ShouldReturnNotValidAccess() {
    when(timePeriod.bounds(calendar.now())).thenReturn(false);

    boolean accessGranted = bikeParkingAccessValidator.validate(new BikeParkingPass(BIKE_PARKING_PASS_CODE,
                                                                                    timePeriod));

    assertThat(accessGranted).isFalse();
  }
}
