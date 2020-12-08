package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.time.DayOfWeek;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DayOfWeekValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public DayOfWeek A_DAY_OF_WEEK = DayOfWeek.MONDAY;

  private Calendar calendar = mock(Calendar.class);
  private CarParkingPassCode parkingPassCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);

  private DayOfWeekValidatorCarParking dayOfWeekValidator = new DayOfWeekValidatorCarParking(calendar);
  @Mock
  private TimePeriod timePeriod;
  @Mock
  private UserRepository userRepository;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private CarParkingPass parkingPass;
  @Mock
  private User user;

  private TimePeriodDayOfWeek timePeriodDayOfWeek;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    CarParkingPassValidator.setPassRepository(userRepository);
    when(user.getCarParkingPass()).thenReturn(parkingPass);
  }

  @After
  public void clearStatic() {
    CarParkingPassValidator.setPassRepository(null);
    CarParkingPassValidator.passCache.clear();
  }

  @Test
  public void whenValidate_shouldGetCorrespondingPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(userRepository).findBy(parkingPassCode);
  }

  @Test
  public void whenValidate_shouldGetTimePeriodFromPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(parkingPass).getTimePeriod();
  }

  @Test
  public void whenValidate_shouldGetDayOfWeekFromTimePeriod() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(parkingPass).getTimePeriod();
  }

  @Test
  public void whenValidate_shouldCallCalendarNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(calendar).getDayOfWeek();
  }

  @Test
  public void givenNotIncludingDayOfWeek_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    timePeriodDayOfWeek = TimePeriodDayOfWeek.MONDAY;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.getDayOfWeek()).thenReturn(DayOfWeek.SUNDAY);
    when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("VIG_01");

    dayOfWeekValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    dayOfWeekValidator.setNextValidator(nextCarParkingPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
