package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InMemoryCampusAccessRepositoryTest {

  private final UserId A_USER_ID = new UserId();
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final User A_USER = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE);
  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE_STRING = "license plate";
  private final LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
  private final CarId A_CAR_ID = CarId.valueOf("1");
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final int A_YEAR = 2020;
  private final Car A_CAR = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private final CampusAccess A_CAMPUS_ACCESS = new CampusAccess(A_CAMPUS_ACCESS_CODE,
                                                                A_USER,
                                                                A_CAR,
                                                                null);

  private InMemoryCampusAccessRepository campusAccessRepository;

  @Before
  public void setUp() throws Exception {
    campusAccessRepository = new InMemoryCampusAccessRepository();
    campusAccessRepository.clear();
  }

  @Test
  public void whenSavingCampusAccess_campusAccessShouldBeSaved() {
    CampusAccessCode code = campusAccessRepository.save(A_CAMPUS_ACCESS);

    assertThat(campusAccessRepository.findBy(code)).isEqualTo(A_CAMPUS_ACCESS);
  }

  @Test
  public void whenFindingById_shouldReturnCampusAccess() {
    campusAccessRepository.save(A_CAMPUS_ACCESS);

    CampusAccess campusAccess = campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE);

    assertThat(campusAccess).isEqualTo(A_CAMPUS_ACCESS);
  }

  @Test(expected = CampusAccessNotFoundException.class)
  public void givenNoCampusAccessCorrespondingToCampusAccessCode_whenFindingById_shouldThrowCampusAccessNotFoundExcetpion() {
    campusAccessRepository.findBy(A_CAMPUS_ACCESS_CODE);
  }

  @Test
  public void whenFindingByLicensePlate_shouldReturnCampusAccesses() {
    CampusAccess anotherCampusAccessSameLicensePlate = new CampusAccess(new CampusAccessCode(), A_USER, A_CAR, null);
    campusAccessRepository.save(A_CAMPUS_ACCESS);
    campusAccessRepository.save(anotherCampusAccessSameLicensePlate);

    List<CampusAccess> campusAccesses = campusAccessRepository.findBy(new LicensePlate(A_LICENSE_PLATE_STRING));

    assertThat(campusAccesses).containsExactly(A_CAMPUS_ACCESS, anotherCampusAccessSameLicensePlate);
  }

  @Test(expected = CampusAccessNotFoundException.class)
  public void givenNoCampusAccessCorrespondingToLicensePlate_whenFindingLicensePlate_shouldThrowCampusAccessNotFoundExcetpion() {
    campusAccessRepository.findBy(new LicensePlate(A_LICENSE_PLATE_STRING));
  }
}