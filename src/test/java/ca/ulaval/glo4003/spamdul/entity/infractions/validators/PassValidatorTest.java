package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.PassRepositoryNotSetException;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class PassValidatorTest extends PassValidator{

    public static final PassCode A_PASS_CODE = new PassCode();
    public static final TimePeriodDayOfWeek A_TIME_PERIOD_DAY_OF_WEEK = TimePeriodDayOfWeek.FRIDAY;
    public static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
    public static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
    public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

    private PassRepository passRepository = mock(PassRepository.class);
    private Pass pass;
    private PassToValidateDto passToValidateDto = new PassToValidateDto();


    @Override
    public void validate(PassToValidateDto passToValidateDto) { }

    @Before
    public void setUp() {
        PassValidator.setPassRepository(null);
        PassValidator.passCache.clear();
        TimePeriod timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_TIME_PERIOD_DAY_OF_WEEK);
        pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);
    }

    @Test
    public void givenNextValidator_whenNextValidation_shouldCallValidateOnNext() {
        PassValidator nextPassValidator = mock(PassValidator.class);
        setNextValidator(nextPassValidator);

        nextValidation(passToValidateDto);

        verify(nextPassValidator).validate(passToValidateDto);
    }

    @Test
    public void givenNoNextValidator_whenNextValidation_shouldRemoveFromCacheCorrespondingPass() {
        passToValidateDto.passCode = A_PASS_CODE.toString();
        passCache.put(A_PASS_CODE, pass);

        nextValidation(passToValidateDto);

        assertThat(passCache).doesNotContainKey(A_PASS_CODE);
    }

    @Test
    public void givenCacheHit_whenGetCorrespondingPass_shouldReturnRightPass() {
        passCache.put(A_PASS_CODE, pass);

        Pass actual = getCorrespondingPass(A_PASS_CODE);

        assertThat(actual).isEqualTo(pass);
    }

    @Test
    public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldFindPassInRepo(){
        PassValidator.setPassRepository(passRepository);

        getCorrespondingPass(A_PASS_CODE);

        verify(passRepository).findByPassCode(A_PASS_CODE);
    }

    @Test
    public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldAddToCache(){
        when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(pass);
        PassValidator.setPassRepository(passRepository);

        getCorrespondingPass(A_PASS_CODE);

        assertThat(passCache).containsEntry(A_PASS_CODE, pass);
    }

    @Test
    public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldReturnRightPass(){
        when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(pass);
        PassValidator.setPassRepository(passRepository);

        Pass actual = getCorrespondingPass(A_PASS_CODE);

        assertThat(actual).isEqualTo(pass);
    }

    @Test(expected = PassRepositoryNotSetException.class)
    public void givenRepoNotSetAndCacheMiss_whenGetCorrespondingPass_shouldThrowRepoNotSetException(){
        getCorrespondingPass(A_PASS_CODE);
    }


}
