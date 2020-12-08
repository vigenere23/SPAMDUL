package ca.ulaval.glo4003.spamdul.usecases.parking.pass;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryMode;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.DeliveryFeeCalculator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassSender;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PassServiceTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final UserId USER_ID = UserId.valueOf("123");
  private static final PassCode A_PASS_CODE = PassCode.valueOf("123");
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

  private final Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);

  @Mock
  private PassFactory passFactory;
  @Mock
  private PassSender passSender;
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

  private PassService passService;

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

    passService = new PassService(passFactory,
                                  passSender,
                                  passTransactionService,
                                  parkingZoneFeeRepository,
                                  deliveryFeeCalculator,
                                  userRepository);
    when(passFactory.create(A_PARKING_ZONE, A_TIME_PERIOD_DTO)).thenReturn(pass);

    when(parkingZoneFeeRepository.findBy(A_PARKING_ZONE, A_PASS_DTO.timePeriodDto.periodType)).thenReturn(
        A_PARKING_ZONE_FEE);
    when(deliveryFeeCalculator.calculateBy(any())).thenReturn(A_DELIVERY_FEE);
  }

  @Test
  public void whenCreatingPass_shouldCallFactoryToCreateNewPass() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_PASS_DTO);

    verify(passFactory).create(A_PARKING_ZONE, A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingPass_shouldSendPassCode() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_PASS_DTO);

    verify(passSender).sendPass(A_DELIVERY_DTO, A_PASS_CODE);
  }

  @Test
  public void whenCreatingPass_shouldFindParkingFee() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_PASS_DTO);

    verify(parkingZoneFeeRepository).findBy(A_PARKING_ZONE, A_PASS_DTO.timePeriodDto.periodType);
  }

  @Test
  public void whenCreatingPass_shouldAddRevenueToPassBankAccount() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);
    when(deliveryFeeCalculator.calculateBy(any(DeliveryMode.class))).thenReturn(A_DELIVERY_FEE);
    when(parkingZoneFeeRepository.findBy(any(ParkingZone.class), any(PeriodType.class))).thenReturn(A_PARKING_ZONE_FEE);
    Amount expectedTotal = AN_AMOUNT.add(ANOTHER_AMOUNT);

    passService.createPass(A_PASS_DTO);

    verify(passTransactionService, times(1)).addRevenue(expectedTotal);
  }

  @Test
  public void givenAPostalDeliveryMode_whenCreatingPass_shouldCallPostFeeRepositoryToFindFee() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_SECOND_PASS_DTO);

    verify(deliveryFeeCalculator).calculateBy(A_POSTAL_DELIVERY_DTO.deliveryMode);
  }

  @Test
  public void whenCreatingPass_shouldFindUserInRepo() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_SECOND_PASS_DTO);

    verify(userRepository).findBy(USER_ID);
  }

  @Test
  public void whenCreatingPass_shouldAskUserToAssociatePass() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_SECOND_PASS_DTO);

    verify(user).associate(pass);
  }

  @Test
  public void whenAssociatingPassToUser_shouldSaveUser() {
    when(userRepository.findBy(USER_ID)).thenReturn(user);

    passService.createPass(A_SECOND_PASS_DTO);

    verify(userRepository).save(user);
  }
}
