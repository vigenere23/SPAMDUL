package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionCode;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserAlreadyHasACampusAccess;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserAlreadyHasARechargULCard;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserAlreadyHasThisInfraction;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class UserTest {

  public static final CarId CAR_ID = new CarId();
  public static final CarType CAR_TYPE = CarType.ECONOMIQUE;
  public static final String BRAND = "brand";
  public static final String MODEL = "model";
  public static final int YEAR = 2020;
  public static final LicensePlate LICENSE_PLATE = new LicensePlate("xxx xxx");
  public static final Car CAR = new Car(CAR_ID, CAR_TYPE, BRAND, MODEL, YEAR, LICENSE_PLATE);
  public static final TimePeriod TIME_PERIOD = new TimePeriod(LocalDateTime.MIN,
                                                              LocalDateTime.MAX,
                                                              TimePeriodDayOfWeek.ALL);
  public static final PassCode PASS_CODE = new PassCode();
  public static final ParkingZone PARKING_ZONE = ParkingZone.ZONE_1;
  public static final LicensePlate ANOTHER_LICENSE_PLATE = new LicensePlate("abs cba");
  public static final PassCode ANOTHER_PASSE_CODE = new PassCode();
  public static final TransactionFactory TRANSACTION_FACTORY = new TransactionFactory();
  public static final RechargULCardId RECHARG_UL_CARD_ID = new RechargULCardId();
  public static final PeriodType PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  public static final CampusAccessCode CAMPUS_ACCESS_CODE = new CampusAccessCode();
  public static final InfractionId ANOTHER_INFRACTION_ID = new InfractionId();
  public static final InfractionId INFRACTION_ID = new InfractionId();
  public static final String DESCRITION = "descrition";
  public static final InfractionCode CODE = InfractionCode.valueOf("code");
  public static final Amount AMOUNT = Amount.valueOf(10);
  public static final LocalDateTime A_TIME_OF_ACCESS = LocalDateTime.now();
  public static final CampusAccessCode ANOTHER_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(2020, 1, 1, 1, 1);
  public static final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.FRIDAY;
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(1991, 7, 10);
  private final UserId A_USER_ID = new UserId();

  @Test
  public void whenCreatingNewUser_shouldCreateRandomId() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);

    assertThat(user.getUserId()).isNotNull();
  }

  @Test
  public void givenABirthDate_whenCalculatingAge_shouldReturnTheCorrectAge() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    LocalDate TODAY_DATE = LocalDate.of(2020, 9, 22);

    int age = user.getAge(TODAY_DATE);

    int AN_AGE = 29;
    assertThat(age).isEqualTo(AN_AGE);
  }

  @Test
  public void whenAddingANewCar_carShouldBeAddedToUser() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    Car anotherCar = new Car(CAR_ID, CAR_TYPE, BRAND, MODEL, YEAR, ANOTHER_LICENSE_PLATE);

    assertThat(user.doesOwn(LICENSE_PLATE)).isTrue();
    user.associate(anotherCar);
    assertThat(user.doesOwn(ANOTHER_LICENSE_PLATE)).isTrue();
  }

  @Test
  public void givenCarDoesNotBelongToUser_whenVerifyingIfHeOwnsCar_userShouldNotOwnsTheCar() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);

    boolean doesOwns = user.doesOwn(ANOTHER_LICENSE_PLATE);

    assertThat(doesOwns).isFalse();
  }

  @Test
  public void givenNoCampusAccess_whenVerifyingIfUserCanAccessCampus_userShouldNotAccessCampus() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);

    boolean isAccessGrantedToCampus = user.isAccessGrantedToCampus(A_TIME_OF_ACCESS);

    assertThat(isAccessGrantedToCampus).isFalse();
  }

  @Test
  public void whenAddingNewCampusAccess_campusAccessShouldBeAddedToUser() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = new CampusAccess(CAMPUS_ACCESS_CODE, PERIOD_TYPE, TIME_PERIOD);

    user.associate(campusAccess);

    assertThat(user.doesOwn(CAMPUS_ACCESS_CODE)).isTrue();
  }

  @Test(expected = UserAlreadyHasACampusAccess.class)
  public void givenACampusAccess_whenAssociatingAnotherCampusAccess_shouldThrowException() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = new CampusAccess(CAMPUS_ACCESS_CODE, PERIOD_TYPE, TIME_PERIOD);
    CampusAccess anotherCampusAccess = new CampusAccess(ANOTHER_CAMPUS_ACCESS_CODE, PERIOD_TYPE, TIME_PERIOD);
    user.associate(campusAccess);

    user.associate(anotherCampusAccess);
  }

  @Test
  public void givenACampusAccess_whenVerifyingIfCanAccessCampus_shouldCallCampusAccessToGrantAccess() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = mock(CampusAccess.class);
    user.associate(campusAccess);

    user.isAccessGrantedToCampus(A_TIME_OF_ACCESS);

    verify(campusAccess, times(1)).grantAccess(A_TIME_OF_ACCESS);
  }

  @Test
  public void whenAddingNewPass_passShouldBeAddedToUser() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = new CampusAccess(CAMPUS_ACCESS_CODE, PERIOD_TYPE, TIME_PERIOD);
    user.associate(campusAccess);
    Pass pass = new Pass(PASS_CODE, PARKING_ZONE, TIME_PERIOD);

    user.associate(pass);

    assertThat(user.doesOwn(PASS_CODE)).isTrue();
  }

  @Test
  public void givenPassDoesNotBelongToUser_whenVerifyingIfHeOwnsPass_userShouldNotOwnsThePass() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = new CampusAccess(CAMPUS_ACCESS_CODE, PERIOD_TYPE, TIME_PERIOD);
    user.associate(campusAccess);

    boolean doesOwns = user.doesOwn(PASS_CODE);

    assertThat(doesOwns).isFalse();
  }

  @Test
  public void whenAddingNewRechargeUlCard_cardShouldBeAddedToUser() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    RechargULCard rechargULCard = new RechargULCard(RECHARG_UL_CARD_ID, TRANSACTION_FACTORY);

    user.associate(rechargULCard);

    assertThat(user.doesOwn(RECHARG_UL_CARD_ID)).isTrue();
  }

  @Test
  public void givenNoRechargULCard_whenVerifyingIfUserOwnsCard_userShouldNotOwnsCard() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);

    boolean doesOwns = user.doesOwn(RECHARG_UL_CARD_ID);

    assertThat(doesOwns).isFalse();
  }

  @Test
  public void givenDoesNotOwnsCampusAccess_whenVerifyingIfUserOwnsCampus_userShouldNotOwnsCampusAccess() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);

    boolean doesOwns = user.doesOwn(CAMPUS_ACCESS_CODE);

    assertThat(doesOwns).isFalse();
  }

  @Test
  public void whenAddingInfractionToUser_infraction_shouldBeAdded() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    Infraction infraction = new Infraction(INFRACTION_ID, DESCRITION, CODE, AMOUNT);
    Infraction anotherInfraction = new Infraction(ANOTHER_INFRACTION_ID, DESCRITION, CODE, AMOUNT);

    user.associate(infraction);
    user.associate(anotherInfraction);

    List<Infraction> allInfractions = user.getAllInfractions();
    assertThat(allInfractions).contains(infraction);
    assertThat(allInfractions).contains(anotherInfraction);
  }

  @Test(expected = UserAlreadyHasThisInfraction.class)
  public void givenAnInfraction_whenAssociatingInfractionWithSameId_shouldThrowException() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    Infraction infraction = new Infraction(INFRACTION_ID, DESCRITION, CODE, AMOUNT);
    Infraction anotherInfraction = new Infraction(INFRACTION_ID, DESCRITION, CODE, AMOUNT);
    user.associate(infraction);

    user.associate(anotherInfraction);
  }

  @Test
  public void whenPayingInfraction_shouldCallInfractionToPayIt() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    Infraction infraction = mock(Infraction.class);
    when(infraction.getInfractionId()).thenReturn(INFRACTION_ID);
    user.associate(infraction);

    user.pay(INFRACTION_ID);

    verify(infraction, times(1)).pay();
  }

  @Test(expected = UserAlreadyHasARechargULCard.class)
  public void givenAnAssociatedRechargULCard_whenAssociatingAnotherCard_shouldThrowException() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    RechargULCard rechargULCard = new RechargULCard(RECHARG_UL_CARD_ID, TRANSACTION_FACTORY);
    RechargULCard anotherRechargULCard = new RechargULCard(RECHARG_UL_CARD_ID, TRANSACTION_FACTORY);
    user.associate(rechargULCard);

    user.associate(anotherRechargULCard);
  }

  @Test
  public void whenAddingCreditsToRechargULCard_shouldCallRechargULCardToAddCreditsWithAmount() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    RechargULCard rechargULCard = mock(RechargULCard.class);
    user.associate(rechargULCard);

    user.addRechargULCredits(AMOUNT);

    verify(rechargULCard, times(1)).addCredits(AMOUNT);
  }

  @Test
  public void whenCheckingIfCanParkInZone_shouldDelegateToCampusAccess() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = mock(CampusAccess.class);
    user.associate(campusAccess);

    user.canParkInZone(A_PARKING_ZONE);

    verify(campusAccess).canParkInZone(A_PARKING_ZONE);
  }

  @Test
  public void whenCheckingIfHasParkingPassBoundingInstant_shouldDelegateCampusAccess() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = mock(CampusAccess.class);
    user.associate(campusAccess);

    user.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME);

    verify(campusAccess).hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME);
  }

  @Test
  public void whenCheckingIfCanParkOnDayOfWeek_shouldDelegateToCampusAccess() {
    User user = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE, CAR);
    CampusAccess campusAccess = mock(CampusAccess.class);
    user.associate(campusAccess);

    user.canParkOnThisDayOfWeek(A_DAY_OF_WEEK);

    verify(campusAccess).hasParkingRightOnThisDayOfWeek(A_DAY_OF_WEEK);
  }
}