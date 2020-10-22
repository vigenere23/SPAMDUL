package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class TimePeriodBoundaryValidatorTest {

    public static final String A_VALID_PASS_CODE_STRING = "9";
    public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(1000,1,1,0,0);

    private Calendar calendar = mock(Calendar.class);

    private TimePeriodBoundaryValidator timePeriodBoundaryValidator = new TimePeriodBoundaryValidator(calendar);
    private TimePeriod timePeriod = mock(TimePeriod.class);
    private PassRepository passRepository = mock(PassRepository.class);
    private PassToValidateDto passToValidateDto = new PassToValidateDto();
    private Pass pass = mock(Pass.class);

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
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
        when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

        timePeriodBoundaryValidator.validate(passToValidateDto);

        verify(passRepository).findByPassCode(passCode);
    }

    @Test
    public void whenValidate_shouldGetTimePeriodFromPass() {
        passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
        PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
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
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
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
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
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
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
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
        when(passRepository.findByPassCode(passCode)).thenReturn(pass);
        when(pass.getTimePeriod()).thenReturn(timePeriod);
        when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
        when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

        timePeriodBoundaryValidator.validate(passToValidateDto);

        verify(nextPassValidator).validate(passToValidateDto);
    }
}
