package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.WrongDayInfractionException;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import java.time.DayOfWeek;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DayOfWeekValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public DayOfWeek A_DAY_OF_WEEK = DayOfWeek.MONDAY;

  private final Calendar calendar = mock(Calendar.class);
  private final CarParkingPassCode passCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);

  private CarParkingDayOfWeekValidator dayOfWeekValidator;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Mock
  private UserFinderService userFinderService;
  @Mock
  private User user;

  @Before
  public void setUp() {
    dayOfWeekValidator = new CarParkingDayOfWeekValidator(calendar, userFinderService);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
  }

  @Test
  public void whenValidate_shouldFindUser() {
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(true);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(userFinderService).findBy(passCode);
  }


  @Test
  public void whenValidate_shouldCallCalendarNow() {
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(true);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(calendar).getDayOfWeek();
  }

  @Test(expected = WrongDayInfractionException.class)
  public void givenNotIncludingDayOfWeek_whenValidate_shouldThrowInfractionException() {
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(false);

    dayOfWeekValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    dayOfWeekValidator.setNextValidator(nextCarParkingPassValidator);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(true);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
