package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PassValidatorTest extends PassValidator {

  public static final PassCode A_PASS_CODE = PassCode.valueOf("123");
  public static final TimePeriodDayOfWeek A_TIME_PERIOD_DAY_OF_WEEK = TimePeriodDayOfWeek.FRIDAY;
  public static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  @Mock
  User user;
  private Pass pass;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
  }

  @Before
  public void setUp() {
    TimePeriod timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_TIME_PERIOD_DAY_OF_WEEK);
    pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);
    when(user.getPass()).thenReturn(pass);
  }


  @Test
  public void givenNextValidator_whenNextValidation_shouldCallValidateOnNext() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    setNextValidator(nextPassValidator);

    nextValidation(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }


}
