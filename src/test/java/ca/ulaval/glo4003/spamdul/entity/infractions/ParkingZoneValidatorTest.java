package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.ParkingZoneValidator;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ParkingZoneValidatorTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final ParkingZone A_INVALID_PARKING_ZONE = ParkingZone.ZONE_3;
  private static final PassCode A_PASS_CODE = new PassCode();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);
  private static final LocalTime A_TIME = LocalTime.now();
  private static final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  private PassValidator nextValidator;
  private ParkingZoneValidator parkingZoneValidator;

  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  private PassRepository passRepository;

  @Before
  public void setUp() {
    nextValidator = mock(PassValidator.class);
    passRepository = mock(PassRepository.class);
    parkingZoneValidator = new ParkingZoneValidator();
  }

  @Test
  public void givenNextValidator_whenParkingZoneIsValid_thenShouldCallNextValidatorToValidate() {
    parkingZoneValidator.setNextValidator(nextValidator);

    parkingZoneValidator.validate(passToValidateDto);

    verify(nextValidator).validate(passToValidateDto);
  }

  @Test(expected = InfractionException.class)
  public void whenParkingZoneIsInvalid_thenShouldThrowInfractionException() {

    parkingZoneValidator.validate(passToValidateDto);
  }
}
