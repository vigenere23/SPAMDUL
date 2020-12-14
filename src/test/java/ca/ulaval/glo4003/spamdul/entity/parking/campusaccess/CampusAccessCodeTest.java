package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class CampusAccessCodeTest {

  @Test
  public void givenValue_whenCreatingCampusAccessCode_shouldCreateWithValue() {
    String value = "1234";
    CampusAccessCode campusAccessCode = CampusAccessCode.valueOf(value);

    assertThat(campusAccessCode.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentCampusAccessCode_shouldNotBeEqual() {
    CampusAccessCode campusAccessCode = CampusAccessCode.valueOf("1234");
    CampusAccessCode anotherCampusAccessCode = CampusAccessCode.valueOf("5678");

    assertThat(campusAccessCode).isNotEqualTo(anotherCampusAccessCode);
  }

  @Test
  public void whenComparingDifferentCampusAccessCode_shouldNotHaveSameHash() {
    CampusAccessCode campusAccessCode = CampusAccessCode.valueOf("1234");
    CampusAccessCode anotherCampusAccessCode = CampusAccessCode.valueOf("5678");

    assertThat(campusAccessCode.hashCode()).isNotEqualTo(anotherCampusAccessCode.hashCode());
  }

  @Test
  public void whenComparingTheSameCampusAccessCode_shouldBeEqual() {
    CampusAccessCode campusAccessCode = CampusAccessCode.valueOf("1234");
    CampusAccessCode sameCampusAccessCode = CampusAccessCode.valueOf(campusAccessCode.toString());

    assertThat(campusAccessCode).isEqualTo(sameCampusAccessCode);
  }

  @Test
  public void whenComparingTheSameCampusAccessCode_shouldHaveTheSameHash() {
    CampusAccessCode campusAccessCode = CampusAccessCode.valueOf("1234");
    CampusAccessCode sameCampusAccessCode = CampusAccessCode.valueOf(campusAccessCode.toString());

    assertThat(campusAccessCode.hashCode()).isEqualTo(sameCampusAccessCode.hashCode());
  }
}
