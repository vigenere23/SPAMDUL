package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
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
public class ParkingCarParkingCarParkingPassValidatorTest extends CarParkingPassValidator {

  public static final CarParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("123");
  public static final TimePeriodDayOfWeek A_TIME_PERIOD_DAY_OF_WEEK = TimePeriodDayOfWeek.FRIDAY;
  public static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  private CarParkingPass parkingPass;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Mock
  private User user;


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
  }

  @Before
  public void setUp() {
    TimePeriod timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_TIME_PERIOD_DAY_OF_WEEK);
    parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);
    when(user.getCarParkingPass()).thenReturn(parkingPass);
  }


  @Test
  public void givenNextValidator_whenNextValidation_shouldCallValidateOnNext() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    setNextValidator(nextCarParkingPassValidator);

    nextValidation(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
