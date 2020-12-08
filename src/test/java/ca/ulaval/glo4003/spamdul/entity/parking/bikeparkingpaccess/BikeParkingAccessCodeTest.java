package ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class BikeParkingAccessCodeTest {

  public static final String ACCESS_CODE = "1234";
  public static final String ANOTHER_ACCESS_CODE = "5678";

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void givenValue_whenCreatingCampusAccessCode_shouldCreateWithValue() {
    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);

    assertThat(bikeParkingAccessCode.toString()).isEqualTo(ACCESS_CODE);
  }

  @Test
  public void whenComparingDifferentCampusAccessCode_shouldNotBeEqual() {
    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);
    BikeParkingAccessCode anotherBikeParkingAccessCode = BikeParkingAccessCode.valueOf(ANOTHER_ACCESS_CODE);

    assertThat(bikeParkingAccessCode).isNotEqualTo(anotherBikeParkingAccessCode);
  }

  @Test
  public void whenComparingDifferentCampusAccessCode_shouldNotHaveSameHash() {
    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);
    BikeParkingAccessCode anotherBikeParkingAccessCode = BikeParkingAccessCode.valueOf(ANOTHER_ACCESS_CODE);

    assertThat(bikeParkingAccessCode.hashCode()).isNotEqualTo(anotherBikeParkingAccessCode.hashCode());
  }

  @Test
  public void whenComparingTheSameCampusAccessCode_shouldBeEqual() {
    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);
    BikeParkingAccessCode anotherBikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);

    assertThat(bikeParkingAccessCode).isEqualTo(anotherBikeParkingAccessCode);
  }

  @Test
  public void whenComparingTheSameCampusAccessCode_shouldHaveTheSameHash() {
    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);
    BikeParkingAccessCode anotherBikeParkingAccessCode = BikeParkingAccessCode.valueOf(ACCESS_CODE);

    assertThat(bikeParkingAccessCode.hashCode()).isEqualTo(anotherBikeParkingAccessCode.hashCode());
  }
}