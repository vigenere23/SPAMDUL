package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TimePeriodBoundaryValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(1000, 1, 1, 0, 0);

  private Calendar calendar = mock(Calendar.class);
  private CarParkingPassCode parkingPassCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);

  private TimePeriodBoundaryValidator timePeriodBoundaryValidator;

  private TimePeriodBoundaryValidatorCarParking timePeriodBoundaryValidator = new TimePeriodBoundaryValidatorCarParking(calendar);
  @Mock
  private UserFinderService userFinderService;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Mock
  private User user;

  @Before
  public void setUp() {
    timePeriodBoundaryValidator = new TimePeriodBoundaryValidator(calendar, userFinderService);
  }

  @Test
  public void whenValidate_shouldGetCorrespondingUser() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(user.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(userFinderService).findBy(passCode);
  }


  @Test
  public void whenValidate_shouldCallCalendarNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(user.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(calendar).now();
  }

  @Test
  public void whenValidate_shouldAskUserIfTimePeriodBoundsNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(user.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(user).hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME);
  }

  @Test(expected = InvalidPassInfractionException.class)
  public void givenNotBoundingTimePeriod_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(user.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME)).thenReturn(false);

    timePeriodBoundaryValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    timePeriodBoundaryValidator.setNextValidator(nextCarParkingPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(user.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
