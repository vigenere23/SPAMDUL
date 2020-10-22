package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.*;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class InfractionServiceTest {

  public static final String ANY_MESSAGE = "test";
  public static final double ANY_AMOUNT = 598.65;
  public final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  public final String  A_PASS_CODE_STRING = "1";
  public final LocalTime A_TIME_OF_THE_DAY = LocalTime.of(12, 0);
  public final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  public final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  public final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                         A_END_DATE_TIME,
                                                         TimePeriodDayOfWeek.MONDAY);
  public final String AN_INFRACTION_CODE_VALUE = "00";
  public final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE);

  private InfractionService infractionService;
  private InfractionInfoRepository infractionInfoRepository;
  private InfractionRepository infractionRepository;
  private PassRepository passRepository;
  private PassValidator passValidator;
  private TransactionService transactionService;
  private PassToValidateDto passToValidateDto;
  private InfractionFactory infractionFactory;
  private InfractionInfos infractionInfos;
  private Infraction infraction;

  @Before
  public void setUp() throws Exception {
    infractionInfoRepository = Mockito.mock(InfractionInfoRepository.class);
    passRepository = Mockito.mock(PassRepository.class);
    passValidator = Mockito.mock(PassValidator.class);
    infractionRepository = Mockito.mock(InfractionRepository.class);
    transactionService = Mockito.mock(TransactionService.class);
    infractionFactory = Mockito.mock(InfractionFactory.class);

    infractionService = new InfractionService(infractionInfoRepository,
                                    infractionRepository,
                                    transactionService,
                                    infractionFactory,
                                    passValidator);

    passToValidateDto = new PassToValidateDto();
    passToValidateDto.parkingZone = A_PARKING_ZONE;
    passToValidateDto.passCode = A_PASS_CODE_STRING;
    infractionInfos = new InfractionInfos();
    infraction = new Infraction(new InfractionId(), ANY_MESSAGE, AN_INFRACTION_CODE, ANY_AMOUNT);
  }


  @Test
  public void whenGivingExpressionIfNotValid_shouldCallValidationChainValidate() {
    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(passValidator, Mockito.times(1)).validate(passToValidateDto);
  }

  @Test
  public void givenInfractionException_whenGivingExpressionIfNotValid_shouldFindInfractionInfosInRepository() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);

    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(infractionInfoRepository).findBy(InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE));
  }

  @Test
  public void givenInfractionException_whenGivingExpressionIfNotValid_shouldCreateInfractionWithFactory() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);

    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(infractionFactory, Mockito.times(1)).create(infractionInfos);
  }

  @Test
  public void givenInfractionException_whenGivingExpressionIfNotValid_shouldSaveInfractionsInRepo() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);
    when(infractionFactory.create(infractionInfos)).thenReturn(infraction);

    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(infractionRepository, Mockito.times(1)).save(infraction);
  }

  @Test
  public void givenInfractionException_whenGivingExpressionIfNotValid_shouldReturnInfraction() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);
    when(infractionFactory.create(infractionInfos)).thenReturn(infraction);

    Infraction actual = infractionService.giveInfractionIfNotValid(passToValidateDto);

    assertThat(actual).isNotNull();
  }

  @Test
  public void givenNoInfractionException_whenGivingExpressionIfNotValid_shouldReturnNull() {
    Infraction actual = infractionService.giveInfractionIfNotValid(passToValidateDto);

    assertThat(actual).isNull();
  }
}