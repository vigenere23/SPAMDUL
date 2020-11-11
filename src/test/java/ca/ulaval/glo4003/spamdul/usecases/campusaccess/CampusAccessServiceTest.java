package ca.ulaval.glo4003.spamdul.usecases.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.account.MainBankAccount;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFactory;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFee;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessFeeRepository;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessRepository;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarService;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserService;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessServiceTest {

  private static final PassCode A_PASS_CODE = new PassCode();
  private static final UserId A_USER_ID = new UserId();
  private static final CarId A_CAR_ID = new CarId();
  private static final User A_USER = new User(A_USER_ID, "name", Gender.MALE, LocalDate.of(1996, 1, 1));
  public static final CarType A_CAR_TYPE = CarType.GOURMANDE;
  public static final String A_LICENSE_PLATE_STRING = "xxx xxx";
  private static final Car A_CAR = new Car(A_CAR_ID,
                                           A_CAR_TYPE,
                                           "brand",
                                           "model",
                                           2020,
                                           new LicensePlate(A_LICENSE_PLATE_STRING));
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.ALL);
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final Pass A_PASS = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  public static final CampusAccessFee A_CAMPUS_ACCESS_FEE = new CampusAccessFee(10);
  public static final int AN_AMOUNT_VALUE = 0;
  public static final Amount AN_AMOUNT = Amount.valueOf(AN_AMOUNT_VALUE);
  public static final Transaction A_TRANSACTION = new Transaction(AN_AMOUNT,
                                                                  LocalDateTime.now(),
                                                                  TransactionType.CAMPUS_ACCESS);

  private CampusAccess campusAccess;
  @Mock
  private CampusAccessRepository campusAccessRepository;
  @Mock
  private UserService userService;
  @Mock
  private CarService carService;
  @Mock
  private CampusAccessFactory campusAccessFactory;
  private CampusAccessDto campusAccessDto;
  private UserDto userDto;
  private CarDto carDto;
  private AccessingCampusDto accessingCampusDto;
  @Mock
  private PassRepository passRepository;
  @Mock
  private Calendar calendar;
  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private BankRepository bankRepository;
  @Mock
  private CampusAccessFeeRepository campusAccessFeeRepository;
  @Mock
  private MainBankAccount mainBankAccount;
  private TransactionDto transactionDto;

  private CampusAccessService campusAccessService;

  @Before
  public void setUp() throws Exception {
    userDto = new UserDto();
    carDto = new CarDto();
    campusAccessDto = new CampusAccessDto();
    campusAccessDto.userDto = userDto;
    campusAccessDto.carDto = carDto;
    campusAccessDto.timePeriodDto = A_TIME_PERIOD_DTO;
    transactionDto = new TransactionDto();
    transactionDto.carType = A_CAR_TYPE;
    transactionDto.transactionType = TransactionType.CAMPUS_ACCESS;
    transactionDto.amount = AN_AMOUNT_VALUE;
    campusAccessService = new CampusAccessService(userService,
                                                  carService,
                                                  campusAccessFactory,
                                                  campusAccessRepository,
                                                  passRepository,
                                                  calendar,
                                                  bankRepository,
                                                  campusAccessFeeRepository,
                                                  transactionFactory);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, A_TIME_PERIOD);

    accessingCampusDto = new AccessingCampusDto();
    accessingCampusDto.campusAccessCode = A_CAMPUS_ACCESS_CODE;

    when(userService.createUser(userDto)).thenReturn(A_USER);
    when(carService.createCar(carDto)).thenReturn(A_CAR);
    when(campusAccessFeeRepository.findBy(any(CarType.class), any(PeriodType.class))).thenReturn(A_CAMPUS_ACCESS_FEE);
    when(bankRepository.getMainBankAccount()).thenReturn(mainBankAccount);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCreateNewUser() {
    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(userService, times(1)).createUser(userDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCreateNewCar() {
    when(bankRepository.getMainBankAccount()).thenReturn(mainBankAccount);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(carService, times(1)).createCar(carDto);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldCallCampusAccessFactoryToCreateNewCampusAccess() {
    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(campusAccessFactory, times(1)).create(A_USER,
                                                 A_CAR,
                                                 A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldSaveCampusAccessInRepository() {
    when(campusAccessFactory.create(A_USER, A_CAR, A_TIME_PERIOD_DTO)).thenReturn(campusAccess);

    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(campusAccessRepository, times(1)).save(campusAccess);
  }

  @Test
  public void whenCreatingNewCampusAccess_shouldAddNewTransactionToMainBankAccount() {
    when(transactionFactory.create(any(TransactionDto.class))).thenReturn(A_TRANSACTION);
    campusAccessService.createAndSaveNewCampusAccess(campusAccessDto);

    verify(mainBankAccount, times(1)).addTransaction(A_TRANSACTION);
    verify(bankRepository, times(1)).save(mainBankAccount);
  }

  @Test
  public void givenACampusAccessCode_whenVerifyingIfCanAccessCampus_shouldFindTheRightCampusAccessFromCode() {
    when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(A_PASS);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(campusAccessRepository, times(1)).findBy(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void givenALicensePlate_whenVerifyingIfCanAccessCampus_shouldFindTheRightCampusAccessFromLicensePlate() {
    List<CampusAccess> campusAccesses = new ArrayList<>();
    campusAccesses.add(campusAccess);
    LicensePlate licensePlate = new LicensePlate(A_LICENSE_PLATE_STRING);
    accessingCampusDto.campusAccessCode = null;
    accessingCampusDto.licensePlate = licensePlate;
    when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(A_PASS);
    when(campusAccessRepository.findBy(licensePlate)).thenReturn(campusAccesses);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(campusAccessRepository, times(1)).findBy(licensePlate);
  }

  @Test
  public void whenVerifyingIfCanAccessCampus_shouldCallCalendarNow() {
    when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(A_PASS);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);
    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(calendar, times(1)).now();
  }

  @Test
  public void givenGrantedAccess_whenVerifyingIfCanAccessCampus_shouldCallPassRepositoryFind() {
    campusAccess.associatePass(A_PASS_CODE, A_TIME_PERIOD);
    when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(A_PASS);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);

    campusAccessService.grantAccessToCampus(accessingCampusDto);

    verify(passRepository, times(1)).findByPassCode(A_PASS_CODE);
  }

  @Test
  public void givenGrantedAccess_whenVerifyingIfCanAccessCampus_shouldReturnTrue() {
    campusAccess.associatePass(A_PASS_CODE, A_TIME_PERIOD);
    when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(A_PASS);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);
    when(calendar.now()).thenReturn(A_START_DATE_TIME);

    boolean result = campusAccessService.grantAccessToCampus(accessingCampusDto);

    assertThat(result).isTrue();
  }

  @Test
  public void givenAnUnregisteredCampusAccessCode_whenVerifyingIfCanAccessCampus_shouldNotGrantAccess() {
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenThrow(CampusAccessNotFoundException.class);

    boolean isGrantedAccess = campusAccessService.grantAccessToCampus(accessingCampusDto);

    assertThat(isGrantedAccess).isFalse();
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldFindCampusAccessInRepo() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);

    verify(campusAccessRepository).findBy(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldAskCampusAccessToAssociatePass() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);

    verify(campusAccess).associatePass(A_PASS_CODE, A_TIME_PERIOD);
  }

  @Test
  public void whenAssociatingPassToCampusAccess_shouldSaveCampusAccess() {
    CampusAccess campusAccess = mock(CampusAccess.class);
    when(campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE)).thenReturn(campusAccess);

    campusAccessService.associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);

    verify(campusAccessRepository).save(campusAccess);
  }
}