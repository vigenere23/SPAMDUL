package ca.ulaval.glo4003.spamdul.parking.usecases.infraction;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfoRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionInfosDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.WrongDayInfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators.CarParkingPassValidator;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking.usecases.infraction.dto.InfractionDto;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionUseCaseTest {

  public static final String ANY_MESSAGE = "test";
  public static final Amount ANY_AMOUNT = Amount.valueOf(598.65);
  public static final InfractionId AN_INFRACTION_ID = InfractionId.valueOf("123");
  public static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();
  public static final LicensePlate LICENSE_PLATE = new LicensePlate("xxx xxx");

  public final String AN_INFRACTION_CODE_VALUE = "VIG_01";
  public final InfractionCode AN_INFRACTION_CODE = InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE);
  private static final InfractionException AN_INFRACTION_EXCEPTION = new WrongDayInfractionException();

  private InfractionInfosDto infractionInfosDto;
  private Infraction infraction;
  private InfractionPaymentDto infractionPaymentDto;
  private PassToValidateDto passToValidateDto;
  private InfractionUseCase infractionUseCase;
  private InfractionDtoAssembler infractionDtoAssembler;

  @Mock
  private InfractionInfoRepository infractionInfoRepository;
  @Mock
  private CarParkingPassValidator carParkingPassValidator;
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
    infractionDtoAssembler = new InfractionDtoAssembler();
    infractionUseCase = new InfractionUseCase(infractionInfoRepository,
                                              userRepository,
                                              infractionFactory,
                                              carParkingPassValidator,
                                              accessLevelValidator,
                                              infractionTransactionService, infractionDtoAssembler);

    passToValidateDto = new PassToValidateDto();
    passToValidateDto.licensePlate = LICENSE_PLATE;
    infractionInfosDto = new InfractionInfosDto();
    infraction = new Infraction(AN_INFRACTION_ID, ANY_MESSAGE, AN_INFRACTION_CODE, ANY_AMOUNT);
    infractionPaymentDto = new InfractionPaymentDto();
    when(user.pay(AN_INFRACTION_ID)).thenReturn(ANY_AMOUNT);
  }

  @Test
  public void whenGivingInfraction_shouldValidateCallIsMadeWithTheRightAccessLevel() {
    infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(accessLevelValidator).validate(A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenGivingInfractionIfNotValid_shouldCallValidationChainValidate() {
    infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(carParkingPassValidator, Mockito.times(1)).validate(passToValidateDto);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldFindInfractionInfosInRepository() {
    doThrow(AN_INFRACTION_EXCEPTION)
        .when(carParkingPassValidator)
        .validate(passToValidateDto);

    infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(infractionInfoRepository).findBy(InfractionCode.valueOf(AN_INFRACTION_CODE_VALUE));
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldCreateInfractionWithFactory() {
    doThrow(AN_INFRACTION_EXCEPTION)
        .when(carParkingPassValidator)
        .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfosDto);

    infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(infractionFactory, Mockito.times(1)).create(infractionInfosDto);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldAssociateInfractionToUser() {
    doThrow(AN_INFRACTION_EXCEPTION)
        .when(carParkingPassValidator)
        .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfosDto);
    when(infractionFactory.create(infractionInfosDto)).thenReturn(infraction);
    when(userRepository.findBy(LICENSE_PLATE)).thenReturn(user);

    infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    verify(user).associate(infraction);
    verify(userRepository).findBy(LICENSE_PLATE);
    verify(userRepository).save(user);
  }

  @Test
  public void givenInfractionException_whenGivingInfractionIfNotValid_shouldReturnInfraction() {
    doThrow(AN_INFRACTION_EXCEPTION)
        .when(carParkingPassValidator)
        .validate(passToValidateDto);
    when(infractionInfoRepository.findBy(AN_INFRACTION_CODE)).thenReturn(infractionInfosDto);
    when(infractionFactory.create(infractionInfosDto)).thenReturn(infraction);
    when(userRepository.findBy(LICENSE_PLATE)).thenReturn(user);

    InfractionDto actual = infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    assertThat(actual).isNotNull();
  }

  @Test
  public void givenNoInfractionException_whenGivingInfractionIfNotValid_shouldReturnNull() {
    InfractionDto actual = infractionUseCase.giveInfractionIfNotValid(passToValidateDto, A_TEMPORARY_TOKEN);

    assertThat(actual).isNull();
  }

  @Test
  public void whenPayingInfraction_shouldFindUserInRepo() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(userRepository.findBy(AN_INFRACTION_ID)).thenReturn(user);

    infractionUseCase.payInfraction(infractionPaymentDto);

    verify(userRepository).findBy(AN_INFRACTION_ID);
  }

  @Test
  public void whenPayingInfraction_shouldAddRevenueToInfractionBankAccount() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(userRepository.findBy(AN_INFRACTION_ID)).thenReturn(user);

    infractionUseCase.payInfraction(infractionPaymentDto);

    verify(infractionTransactionService).addRevenue(ANY_AMOUNT);
  }

  @Test
  public void whenPayingInfraction_shouldTellUserToPayInfraction() {
    infractionPaymentDto.infractionId = AN_INFRACTION_ID;
    when(userRepository.findBy(AN_INFRACTION_ID)).thenReturn(user);

    infractionUseCase.payInfraction(infractionPaymentDto);

    verify(user).pay(AN_INFRACTION_ID);
  }
}
