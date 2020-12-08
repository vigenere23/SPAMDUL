package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimePeriodBoundaryValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(1000, 1, 1, 0, 0);

  private Calendar calendar = mock(Calendar.class);

  private TimePeriodBoundaryValidator timePeriodBoundaryValidator = new TimePeriodBoundaryValidator(calendar);
  @Mock
  private TimePeriod timePeriod;
  @Mock
  private UserRepository userRepository;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private Pass pass;
  @Mock
  private User user;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    PassValidator.setPassRepository(userRepository);
    when(user.getPass()).thenReturn(pass);
  }

  @After
  public void clearStatic() {
    PassValidator.setPassRepository(null);
    PassValidator.passCache.clear();
  }

  @Test
  public void whenValidate_shouldGetCorrespondingPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(userRepository).findBy(passCode);
  }

  @Test
  public void whenValidate_shouldGetTimePeriodFromPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(pass).getTimePeriod();
  }

  @Test
  public void whenValidate_shouldCallCalendarNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(calendar).now();
  }

  @Test
  public void whenValidate_shouldChekIfTimePeriodBoundsNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(timePeriod).bounds(A_LOCAL_DATE_TIME);
  }

  @Test
  public void givenNotBoundingTimePeriod_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(false);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("VIG_02");

    timePeriodBoundaryValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    timePeriodBoundaryValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
