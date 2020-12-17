package ca.ulaval.glo4003.spamdul.parking.usecases.pass;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassSender;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDto;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ParkingParkingPassUseCaseTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final UserId USER_ID = UserId.valueOf("123");
  private static final ParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("123");
  private static final DeliveryDto A_DELIVERY_DTO = new DeliveryDto();
  private static final DeliveryDto A_POSTAL_DELIVERY_DTO = new DeliveryDto();
  private static final DeliveryMode A_POSTAL_DELIVERY_MODE = DeliveryMode.POST;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private static final PassDto A_PASS_DTO = new PassDto();
  private static final PassDto A_SECOND_PASS_DTO = new PassDto();
  private static final Amount AN_AMOUNT = Amount.valueOf(5.23);
  private static final Amount ANOTHER_AMOUNT = Amount.valueOf(15.34);
  private static final Amount A_PARKING_ZONE_FEE = AN_AMOUNT;
  private static final Amount A_DELIVERY_FEE = ANOTHER_AMOUNT;

  private final ParkingPass parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);

  @Mock
  private ParkingPassFactory parkingPassFactory;
  @Mock
  private ParkingPassSender parkingPassSender;
  @Mock
  PassTransactionService passTransactionService;
  @Mock
  ParkingZoneFeeRepository parkingZoneFeeRepository;
  @Mock
  DeliveryFeeCalculator deliveryFeeCalculator;
  @Mock
  UserRepository userRepository;
  @Mock
  User user;

  private ParkingPassUseCase parkingPassUseCase;

  @Before
  public void setUp() {
    A_PASS_DTO.userId = USER_ID;
    A_PASS_DTO.deliveryDto = A_DELIVERY_DTO;
    A_PASS_DTO.parkingZone = A_PARKING_ZONE;
    A_PASS_DTO.timePeriodDto = A_TIME_PERIOD_DTO;

    A_POSTAL_DELIVERY_DTO.deliveryMode = A_POSTAL_DELIVERY_MODE;
    A_SECOND_PASS_DTO.userId = USER_ID;
    A_SECOND_PASS_DTO.deliveryDto = A_POSTAL_DELIVERY_DTO;
    A_SECOND_PASS_DTO.parkingZone = A_PARKING_ZONE;
    A_SECOND_PASS_DTO.timePeriodDto = A_TIME_PERIOD_DTO;

    parkingPassUseCase = new ParkingPassUseCase(parkingPassFactory,
                                                parkingPassSender,
                                                passTransactionService,
                                                parkingZoneFeeRepository,
                                                deliveryFeeCalculator,
                                                userRepository);
    when(parkingPassFactory.create(A_PARKING_ZONE, A_TIME_PERIOD_DTO)).thenReturn(parkingPass);

    when(parkingZoneFeeRepository.findBy(A_PARKING_ZONE, A_PASS_DTO.timePeriodDto.periodType)).thenReturn(
        A_PARKING_ZONE_FEE);
    when(deliveryFeeCalculator.calculateBy(any())).thenReturn(A_DELIVERY_FEE);
  }

  @Test
  public void whenCreatingPass_shouldCallFactoryToCreateNewPass() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_PASS_DTO);

    verify(parkingPassFactory).create(A_PARKING_ZONE, A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingPass_shouldSendPassCode() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_PASS_DTO);

    verify(parkingPassSender).sendPass(A_DELIVERY_DTO, A_PASS_CODE);
  }

  @Test
  public void whenCreatingPass_shouldFindParkingFee() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_PASS_DTO);

    verify(parkingZoneFeeRepository).findBy(A_PARKING_ZONE, A_PASS_DTO.timePeriodDto.periodType);
  }

  @Test
  public void whenCreatingPass_shouldAddRevenueToPassBankAccount() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);
    when(deliveryFeeCalculator.calculateBy(any(DeliveryMode.class))).thenReturn(A_DELIVERY_FEE);
    when(parkingZoneFeeRepository.findBy(any(ParkingZone.class), any(PeriodType.class))).thenReturn(A_PARKING_ZONE_FEE);
    Amount expectedTotal = AN_AMOUNT.add(ANOTHER_AMOUNT);

    parkingPassUseCase.createPass(A_PASS_DTO);

    verify(passTransactionService).addRevenue(expectedTotal);
  }

  @Test
  public void givenAPostalDeliveryMode_whenCreatingPass_shouldCallPostFeeRepositoryToFindFee() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_SECOND_PASS_DTO);

    verify(deliveryFeeCalculator).calculateBy(A_POSTAL_DELIVERY_DTO.deliveryMode);
  }

  @Test
  public void whenCreatingPass_shouldFindUserInRepo() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_SECOND_PASS_DTO);

    verify(userRepository).findBy(USER_ID);
  }

  @Test
  public void whenCreatingPass_shouldAskUserToAssociatePass() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_SECOND_PASS_DTO);

    verify(user).associate(parkingPass);
  }

  @Test
  public void whenAssociatingPassToUser_shouldSaveUser() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    parkingPassUseCase.createPass(A_SECOND_PASS_DTO);

    verify(userRepository).save(user);
  }
}
