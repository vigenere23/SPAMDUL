package ca.ulaval.glo4003.spamdul.usecases.infraction;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionInfos;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.validators.PassValidator;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionServiceTest {

  private static final String ANY_MESSAGE = "test";
  private static final Amount ANY_AMOUNT = Amount.valueOf(598.65);
  private static final InfractionId AN_INFRACTION_ID = InfractionId.valueOf("123");
  private static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();
  private static final LicensePlate LICENSE_PLATE = new LicensePlate("xxx xxx");
  private static final String AN_INFRACTION_CODE_VALUE = "00";
  private static final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE);

  private InfractionService infractionService;
  private PassToValidateDto passToValidateDto;
  private InfractionInfos infractionInfos;
  private Infraction infraction;
  private InfractionPaymentDto infractionPaymentDto;

  @Mock
  private InfractionInfoRepository infractionInfoRepository;
  @Mock
  private PassValidator passValidator;
  @Mock
  private InfractionFactory infractionFactory;
  @Mock
  private AccessLevelValidator accessLevelValidator;
  @Mock
  private InfractionTransactionService infractionTransactionService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    infractionService = new InfractionService(infractionInfoRepository,
                                              userRepository,
                                              infractionFactory,
                                              passValidator,
                                              accessLevelValidator,
                                              infractionTransactionService);

    passToValidateDto = new PassToValidateDto();
    passToValidateDto.licensePlate = LICENSE_PLATE;
    infractionInfos = new InfractionInfos();
    infraction = new Infraction(AN_INFRACTION_ID, ANY_MESSAGE, AN_INFRACTION_CODE, ANY_AMOUNT);
    infractionPaymentDto = new InfractionPaymentDto();
    when(user.pay(AN_INFRACTION_ID)).thenReturn(ANY_AMOUNT);
  }

  @Test
  public void whenGivingInfraction_shouldValidateCallIsMadeWithTheRightAccessLevel() {
    infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator, times(1)).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGivingInfractionIfNotValid_shouldCallValidationChainValidate() {
    infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(passValidator, Mockito.times(1)).validate(passToValidateDto);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldFindInfractionInfosInRepository() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
        .when(passValidator)
        .validate(passToValidateDto);

    infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(infractionInfoRepository).findBy(InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE));
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldCreateInfractionWithFactory() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
        .when(passValidator)
        .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);

    infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(infractionFactory, Mockito.times(1)).create(infractionInfos);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldAssociateInfractionToUser() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
        .when(passValidator)
        .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);
    when(infractionFactory.create(infractionInfos)).thenReturn(infraction);
    when(userRepository.findBy(LICENSE_PLATE)).thenReturn(user);

    infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(user, Mockito.times(1)).associate(infraction);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldReturnInfraction() {
    doThrow(new InfractionException(AN_INFRACTION_CODE_VALUE))
        .when(passValidator)
        .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfos);
    when(infractionFactory.create(infractionInfos)).thenReturn(infraction);
    when(userRepository.findBy(LICENSE_PLATE)).thenReturn(user);

    Infraction actual = infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    assertThat(actual).isNotNull();
  }

  @Test
  public void givenNoInfractionException_whenGivingInfractionIfNotValid_shouldReturnNull() {
    Infraction actual = infractionService.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    assertThat(actual).isNull();
  }

  @Test
  public void whenPayingInfraction_shouldFindUserInRepo() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(userRepository.findBy(AN_INFRACTION_ID)).thenReturn(user);

    infractionService.payInfraction(infractionPaymentDto);

    verify(userRepository).findBy(AN_INFRACTION_ID);
  }

  @Test
  public void whenPayingInfraction_shouldAddRevenueToInfractionBankAccount() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(userRepository.findBy(AN_INFRACTION_ID)).thenReturn(user);

    infractionService.payInfraction(infractionPaymentDto);

    verify(infractionTransactionService, times(1)).addRevenue(ANY_AMOUNT);
  }

  @Test
  public void whenPayingInfraction_shouldTellUserToPayInfraction() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(userRepository.findBy(AN_INFRACTION_ID)).thenReturn(user);

    infractionService.payInfraction(infractionPaymentDto);

    verify(user, times(1)).pay(AN_INFRACTION_ID);
  }
}
