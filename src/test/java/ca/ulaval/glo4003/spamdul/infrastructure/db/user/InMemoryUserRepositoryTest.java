package ca.ulaval.glo4003.spamdul.infrastructure.db.user;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;

public class InMemoryUserRepositoryTest {

  public static final TimePeriod TIME_PERIOD = new TimePeriod(LocalDateTime.MIN,
                                                              LocalDateTime.MAX,
                                                              TimePeriodDayOfWeek.ALL);
  public static final ParkingZone PARKING_ZONE = ParkingZone.ZONE_1;
  public static final CarParkingPassCode PASS_CODE = CarParkingPassCode.valueOf("123");
  public static final Amount AMOUNT = Amount.valueOf(10);
  public static final InfractionId INFRACTION_ID = InfractionId.valueOf("123");
  public static final String DESCRIPTION = "description";
  public static final InfractionCode CODE = InfractionCode.valueOf("code");
  public static final TransactionFactory TRANSACTION_FACTORY = new TransactionFactory();
  public static final RechargULCardId RECHARG_UL_CARD_ID = RechargULCardId.valueOf("123");
  public static final String BIKE_PARKING_ACCESS_CODE_STRING = "1234";
  public static final BikeParkingPassCode BIKE_PARKING_ACCESS_CODE = BikeParkingPassCode.valueOf(
      BIKE_PARKING_ACCESS_CODE_STRING);
  public static final BikeParkingPass BIKE_PARKING_ACCESS = new BikeParkingPass(BIKE_PARKING_ACCESS_CODE,
                                                                                TIME_PERIOD);
  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE_STRING = "license plate";
  private final LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
  private final CarId A_CAR_ID = CarId.valueOf("1");
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final int A_YEAR = 2020;
  private final Car A_CAR = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
  private final UserId A_USER_ID = UserId.valueOf("123");
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final User A_USER = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, A_CAR);
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("123");
  private final CampusAccess A_CAMPUS_ACCESS = new CampusAccess(A_CAMPUS_ACCESS_CODE,
                                                                TIME_PERIOD);
  private final CarParkingPass A_CAR_PARKING_PASS = new CarParkingPass(PASS_CODE, PARKING_ZONE, TIME_PERIOD);

  private InMemoryUserRepository userRepository;

  @Before
  public void setUp() throws Exception {
    userRepository = new InMemoryUserRepository();
    userRepository.deleteAll();
  }

  @Test
  public void whenSavingUser_userShouldBeSaved() {
    userRepository.save(A_USER);

    assertThat(userRepository.findBy(A_USER_ID)).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingById_shouldReturnUser() {
    userRepository.save(A_USER);

    User user = userRepository.findBy(A_USER_ID);

    assertThat(user).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingByCampusAccess_shouldReturnUser() {
    A_USER.associate(A_CAMPUS_ACCESS);
    userRepository.save(A_USER);

    User user = userRepository.findBy(A_CAMPUS_ACCESS_CODE);

    assertThat(user).isEqualTo(A_USER);
  }


  @Test
  public void whenFindingByLicensePlate_shouldReturnUser() {
    userRepository.save(A_USER);

    User user = userRepository.findBy(new LicensePlate(A_LICENSE_PLATE_STRING));

    assertThat(user).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingByPassCode_shouldReturnUser() {
    A_USER.associate(A_CAMPUS_ACCESS);
    A_USER.associateCarParkingPass(A_CAR_PARKING_PASS);
    userRepository.save(A_USER);

    User user = userRepository.findBy(PASS_CODE);

    assertThat(user).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingByInfractionId_shouldReturnUser() {
    A_USER.associate(new Infraction(INFRACTION_ID, DESCRIPTION, CODE, AMOUNT));
    userRepository.save(A_USER);

    User user = userRepository.findBy(INFRACTION_ID);

    assertThat(user).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingByRechargUlCard_shouldReturnUser() {
    A_USER.associate(new RechargULCard(RECHARG_UL_CARD_ID, TRANSACTION_FACTORY));
    userRepository.save(A_USER);

    User user = userRepository.findBy(RECHARG_UL_CARD_ID);

    assertThat(user).isEqualTo(A_USER);
  }

  @Test
  public void whenFindingByBikeParkingAccess_shouldReturnUser() {
    A_USER.associateBikeParkingPass(BIKE_PARKING_ACCESS);
    userRepository.save(A_USER);

    User user = userRepository.findBy(BIKE_PARKING_ACCESS_CODE);

    assertThat(user).isEqualTo(A_USER);
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoUserCorrespondingToId_whenFindingById_shouldThrowException() {
    userRepository.findBy(UserId.valueOf("465"));
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoUserCorrespondingToCampusAccessCode_whenFindingById_shouldThrowException() {
    userRepository.findBy(CampusAccessCode.valueOf("4534"));
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoUserCorrespondingToLicensePlate_whenFindingLicensePlate_shouldThrowException() {
    userRepository.findBy(new LicensePlate(A_LICENSE_PLATE_STRING));
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoUserCorrespondingToPassCode_whenFindingByPassCode_shouldThrowException() {
    userRepository.findBy(CarParkingPassCode.valueOf("345"));
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoUserCorrespondingToRechargULCardId_whenFindingByRechargULCardId_shouldThrowException() {
    userRepository.findBy(RechargULCardId.valueOf("567"));
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoInfractionCorrespondingToInfractionId_whenFindingByInfractionId_shouldThrowException() {
    userRepository.findBy(InfractionId.valueOf("8754"));
  }

  @Test(expected = UserNotFoundException.class)
  public void givenNoBikeParkingAccessCorrespondingToAccessCode_whenFindingByBikeParkingAccessCode_shouldThrowException() {
    userRepository.findBy(BikeParkingPassCode.valueOf("1234"));
  }
}
