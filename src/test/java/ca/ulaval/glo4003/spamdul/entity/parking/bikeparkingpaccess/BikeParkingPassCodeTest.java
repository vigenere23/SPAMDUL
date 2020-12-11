package ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import org.junit.Before;
import org.junit.Test;

public class BikeParkingPassCodeTest {

  public static final String ACCESS_CODE = "1234";
  public static final String ANOTHER_ACCESS_CODE = "5678";

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void givenValue_whenCreatingCampusAccessCode_shouldCreateWithValue() {
    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);

    assertThat(bikeParkingPassCode.toString()).isEqualTo(ACCESS_CODE);
  }

  @Test
  public void whenComparingDifferentCampusAccessCode_shouldNotBeEqual() {
    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);
    BikeParkingPassCode anotherBikeParkingPassCode = BikeParkingPassCode.valueOf(ANOTHER_ACCESS_CODE);

    assertThat(bikeParkingPassCode).isNotEqualTo(anotherBikeParkingPassCode);
  }

  @Test
  public void whenComparingDifferentCampusAccessCode_shouldNotHaveSameHash() {
    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);
    BikeParkingPassCode anotherBikeParkingPassCode = BikeParkingPassCode.valueOf(ANOTHER_ACCESS_CODE);

    assertThat(bikeParkingPassCode.hashCode()).isNotEqualTo(anotherBikeParkingPassCode.hashCode());
  }

  @Test
  public void whenComparingTheSameCampusAccessCode_shouldBeEqual() {
    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);
    BikeParkingPassCode anotherBikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);

    assertThat(bikeParkingPassCode).isEqualTo(anotherBikeParkingPassCode);
  }

  @Test
  public void whenComparingTheSameCampusAccessCode_shouldHaveTheSameHash() {
    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);
    BikeParkingPassCode anotherBikeParkingPassCode = BikeParkingPassCode.valueOf(ACCESS_CODE);

    assertThat(bikeParkingPassCode.hashCode()).isEqualTo(anotherBikeParkingPassCode.hashCode());
  }
}