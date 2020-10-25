package ca.ulaval.glo4003.spamdul.usecases.pass;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFee;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.sale.PassSender;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PassServiceTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final PassCode A_PASS_CODE = new PassCode();
  private static final DeliveryDto A_DELIVERY_DTO = new DeliveryDto();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private static final PassDto A_PASS_SALE_DTO = new PassDto();
  private static final double A_TRANSACTION_AMOUNT = 5.0;
  private static final ParkingZoneFee A_PARKING_ZONE_FEE = new ParkingZoneFee(A_TRANSACTION_AMOUNT);


  private Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);

  @Mock
  private PassFactory passFactory;
  @Mock
  private PassRepository passRepository;
  @Mock
  private CampusAccessService campusAccessService;
  @Mock
  private PassSender passSender;
  @Mock
  TransactionFactory transactionFactory;
  @Mock
  BankRepository bankRepository;
  @Mock
  ParkingZoneFeeRepository parkingZoneFeeRepository;
  @Mock
  MainBankAccount mainBankAccount;

  private PassService passService;

  @Before
  public void setUp() {
    A_PASS_SALE_DTO.campusAccessCode = A_CAMPUS_ACCESS_CODE;
    A_PASS_SALE_DTO.deliveryDto = A_DELIVERY_DTO;
    A_PASS_SALE_DTO.parkingZone = A_PARKING_ZONE;
    A_PASS_SALE_DTO.timePeriodDto = A_TIME_PERIOD_DTO;

    passService = new PassService(passRepository,
                                  passFactory,
                                  campusAccessService,
                                  passSender,
                                  transactionFactory,
                                  bankRepository,
                                  parkingZoneFeeRepository);
    when(passFactory.create(A_PARKING_ZONE, A_TIME_PERIOD_DTO)).thenReturn(pass);

    when(parkingZoneFeeRepository.findBy(A_PARKING_ZONE, A_PASS_SALE_DTO.timePeriodDto.periodType)).thenReturn(
        A_PARKING_ZONE_FEE);
    when(bankRepository.getMainBankAccount()).thenReturn(mainBankAccount);
  }

  @Test
  public void whenCreatingPass_shouldCallFactoryToCreateNewPass() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(passFactory).create(A_PARKING_ZONE, A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingPass_shouldCallCampusAccessServiceToAssociatePassToAccess() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(campusAccessService).associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);
  }

  @Test
  public void whenCreatingPass_shouldAddPassToRepository() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(passRepository).save(pass);
  }

  @Test
  public void whenCreatingPass_shouldSendPassCode() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(passSender).sendPass(A_DELIVERY_DTO, A_PASS_CODE);
  }

  @Test
  public void whenCreatingPass_thenShouldFindParkingFee() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(parkingZoneFeeRepository).findBy(A_PARKING_ZONE, A_PASS_SALE_DTO.timePeriodDto.periodType);
  }

  @Test
  public void whenCreatingPass_thenShouldCreateTransaction() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(transactionFactory).create(any(TransactionDto.class));
  }

  @Test
  public void whenCreatingPass_thenShouldGetMainBankAccountFromBankRepository() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(bankRepository).getMainBankAccount();
  }

  @Test
  public void whenCreatingPass_thenShouldAddTransactionToMainBankAccount() {
    passService.createPass(A_PASS_SALE_DTO);

    verify(mainBankAccount).addTransaction(any(Transaction.class));
  }
}
