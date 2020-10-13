package ca.ulaval.glo4003.spamdul.usecases.InfractionService;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class InfractionServiceTest {

  public final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  public final PassCode A_PASS_CODE = PassCode.valueOf("1");
  public final LocalTime A_TIME_OF_THE_DAY = LocalTime.of(12, 0);
  public final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  public final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  public final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                         A_END_DATE_TIME,
                                                         TimePeriodDayOfWeek.MONDAY);
  public final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  public final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf("00");

  private InfractionService service;
  private InfractionRepository infractionRepository;
  private PassRepository passRepository;
  private PassValidator passValidator;
  private InfractionValidationDto infractionValidationDto;

  @Before
  public void setUp() throws Exception {
    infractionRepository = BDDMockito.mock(InfractionRepository.class);
    passRepository = BDDMockito.mock(PassRepository.class);
    passValidator = BDDMockito.mock(PassValidator.class);
    service = new InfractionService(infractionRepository, passRepository, passValidator);

    infractionValidationDto = new InfractionValidationDto();
    infractionValidationDto.parkingZone = A_PARKING_ZONE;
    infractionValidationDto.passCode = A_PASS_CODE;
    infractionValidationDto.time = A_TIME_OF_THE_DAY;
  }

  @Test
  public void whenValidatingPass_shouldRetrievePass() {
    service.validatePass(infractionValidationDto);

    BDDMockito.verify(passRepository, Mockito.times(1)).findByPassCode(A_PASS_CODE);
  }

  @Test
  public void whenValidatingPass_shouldValidatePass() {
    BDDMockito.given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);

    service.validatePass(infractionValidationDto);

    BDDMockito.verify(passValidator, Mockito.times(1)).validate(A_PASS, A_PARKING_ZONE, A_TIME_OF_THE_DAY);
  }

  @Test
  public void whenValidatingPass_shouldFindInfractionInRepository() {
    BDDMockito.given(passRepository.findByPassCode(A_PASS_CODE)).willReturn(A_PASS);
    BDDMockito.given(passValidator.validate(A_PASS, A_PARKING_ZONE, A_TIME_OF_THE_DAY)).willReturn(AN_INFRACTION_CODE);

    service.validatePass(infractionValidationDto);

    BDDMockito.verify(infractionRepository, Mockito.times(1)).findBy(AN_INFRACTION_CODE);
  }
}