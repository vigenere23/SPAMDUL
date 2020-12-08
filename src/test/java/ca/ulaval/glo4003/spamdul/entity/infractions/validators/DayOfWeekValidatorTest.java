package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.WrongDayInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DayOfWeekValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public DayOfWeek A_DAY_OF_WEEK = DayOfWeek.MONDAY;

  private Calendar calendar = mock(Calendar.class);

  private DayOfWeekValidator dayOfWeekValidator;

  @Mock
  private UserFinderService userFinderService;

  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Mock
  private User user;


  @Before
  public void setUp() {
    dayOfWeekValidator = new DayOfWeekValidator(calendar, userFinderService);
  }

  @Test
  public void whenValidate_shouldFindUser() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(true);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(userFinderService).findBy(passCode);
  }


  @Test
  public void whenValidate_shouldCallCalendarNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(true);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(calendar).getDayOfWeek();
  }

  @Test(expected = WrongDayInfractionException.class)
  public void givenNotIncludingDayOfWeek_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(false);

    dayOfWeekValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    dayOfWeekValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
    when(user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK)).thenReturn(true);

    dayOfWeekValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
