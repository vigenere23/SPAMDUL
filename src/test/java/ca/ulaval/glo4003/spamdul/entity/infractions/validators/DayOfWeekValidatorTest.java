package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class DayOfWeekValidatorTest {

    public static final String A_VALID_PASS_CODE_STRING = "9";
    public DayOfWeek A_DAY_OF_WEEK = DayOfWeek.MONDAY;

    private Calendar calendar = mock(Calendar.class);

    private DayOfWeekValidator dayOfWeekValidator = new DayOfWeekValidator(calendar);
    private TimePeriod timePeriod = mock(TimePeriod.class);
    private PassRepository passRepository = mock(PassRepository.class);
    private PassToValidateDto passToValidateDto = new PassToValidateDto();
    private Pass pass = mock(Pass.class);

    private TimePeriodDayOfWeek timePeriodDayOfWeek;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        PassValidator.setPassRepository(passRepository);
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
        timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
        when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

        dayOfWeekValidator.validate(passToValidateDto);

        verify(passRepository).findByPassCode(passCode);
    }

    @Test
    public void whenValidate_shouldGetTimePeriodFromPass() {
        passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
        PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
        timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
        when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

        dayOfWeekValidator.validate(passToValidateDto);

        verify(pass).getTimePeriod();
    }

    @Test
    public void whenValidate_shouldGetDayOfWeekFromTimePeriod() {
        passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
        PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
        timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
        when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

        dayOfWeekValidator.validate(passToValidateDto);

        verify(pass).getTimePeriod();
    }

    @Test
    public void whenValidate_shouldCallCalendarNow() {
        passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
        PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
        timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
        when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

        dayOfWeekValidator.validate(passToValidateDto);

        verify(calendar).getDayOfWeek();
    }

    @Test
    public void givenNotIncludingDayOfWeek_whenValidate_shouldThrowInfractionException() {
        passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
        PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
        timePeriodDayOfWeek = TimePeriodDayOfWeek.MONDAY;
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.getDayOfWeek()).thenReturn(DayOfWeek.SUNDAY);
        when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

        exceptionRule.expect(InfractionException.class);
        exceptionRule.expectMessage("VIG_01");

        dayOfWeekValidator.validate(passToValidateDto);
    }

    @Test
    public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
        PassValidator nextPassValidator = mock(PassValidator.class);
        dayOfWeekValidator.setNextValidator(nextPassValidator);
        passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
        PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
        timePeriodDayOfWeek = TimePeriodDayOfWeek.ALL;
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.getDayOfWeek()).thenReturn(A_DAY_OF_WEEK);
        when(timePeriod.getTimePeriodDayOfWeek()).thenReturn(timePeriodDayOfWeek);

        dayOfWeekValidator.validate(passToValidateDto);

        verify(nextPassValidator).validate(passToValidateDto);
    }
}
