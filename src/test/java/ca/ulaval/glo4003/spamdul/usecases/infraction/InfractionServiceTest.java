package ca.ulaval.glo4003.spamdul.usecases.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.*;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class InfractionServiceTest {

  public static final String ANY_MESSAGE = "test";
  public static final double ANY_AMOUNT = 598.65;
  public static final InfractionId AN_INFRACTION_ID = new InfractionId();

  public final String AN_INFRACTION_CODE_VALUE = "00";
  public final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE);

  private InfractionService infractionService;
  private InfractionInfoRepository infractionInfoRepository;
  private InfractionRepository infractionRepository;
  private PassValidator passValidator;
  private TransactionService transactionService;
  private PassToValidateDto passToValidateDto;
  private InfractionFactory infractionFactory;
  private InfractionInfos infractionInfos;
  private Infraction infraction;
  private InfractionPaymentDto infractionPaymentDto;

  @Before
  public void setUp() throws Exception {
    infractionInfoRepository = Mockito.mock(InfractionInfoRepository.class);
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
    infractionInfos = new InfractionInfos();
    infraction = new Infraction(new InfractionId(), ANY_MESSAGE, AN_INFRACTION_CODE, ANY_AMOUNT);
    infractionPaymentDto = new InfractionPaymentDto();
  }


  @Test
  public void whenGivingInfractionIfNotValid_shouldCallValidationChainValidate() {
    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(passValidator, Mockito.times(1)).validate(passToValidateDto);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldFindInfractionInfosInRepository() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);

    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(infractionInfoRepository).findBy(InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE));
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldCreateInfractionWithFactory() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);

    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(infractionFactory, Mockito.times(1)).create(infractionInfos);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldSaveInfractionsInRepo() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);
    when(infractionFactory.create(infractionInfos)).thenReturn(infraction);

    infractionService.giveInfractionIfNotValid(passToValidateDto);

    verify(infractionRepository, Mockito.times(1)).save(infraction);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldReturnInfraction() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
            .when(passValidator)
            .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);
    when(infractionFactory.create(infractionInfos)).thenReturn(infraction);

    Infraction actual = infractionService.giveInfractionIfNotValid(passToValidateDto);

    assertThat(actual).isNotNull();
  }

  @Test
  public void givenNoInfractionException_whenGivingInfractionIfNotValid_shouldReturnNull() {
    Infraction actual = infractionService.giveInfractionIfNotValid(passToValidateDto);

    assertThat(actual).isNull();
  }

  @Test
  public void whenPayingInfraction_shouldFindInfractionInRepo() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(infractionRepository.findBy(AN_INFRACTION_ID)).thenReturn(infraction);

    infractionService.payInfraction(infractionPaymentDto);

    verify(infractionRepository).findBy(AN_INFRACTION_ID);
  }

  @Test
  public void whenPayingInfraction_shouldCreateTransaction() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(infractionRepository.findBy(AN_INFRACTION_ID)).thenReturn(infraction);

    infractionService.payInfraction(infractionPaymentDto);

    verify(transactionService).createTransaction(any(TransactionDto.class));
  }

  @Test
  public void whenPayingInfraction_shouldPay() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(infractionRepository.findBy(AN_INFRACTION_ID)).thenReturn(infraction);

    infractionService.payInfraction(infractionPaymentDto);

    assertThat(infraction.isPaid()).isTrue();
  }
}