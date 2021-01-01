package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class ParkingAccessLogIdTest {

  @Test
  public void givenValue_whenCreatingParkingAccessLogId_shouldCreateWithValue() {
    String value = "123";
    ParkingAccessLogId parkingAccessLogId = ParkingAccessLogId.valueOf(value);

    assertThat(parkingAccessLogId.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentParkingAccessLogId_shouldNotBeEqual() {
    ParkingAccessLogId parkingAccessLogId = ParkingAccessLogId.valueOf("123");
    ParkingAccessLogId anotherParkingAccessLogId = ParkingAccessLogId.valueOf("456");

    assertThat(parkingAccessLogId).isNotEqualTo(anotherParkingAccessLogId);
    assertThat(parkingAccessLogId.hashCode()).isNotEqualTo(anotherParkingAccessLogId.hashCode());
  }

  @Test
  public void whenComparingTheSameParkingAccessLogId_shouldBeEqual() {
    ParkingAccessLogId parkingAccessLogId = ParkingAccessLogId.valueOf("123");
    ParkingAccessLogId sameParkingAccessLogId = ParkingAccessLogId.valueOf(parkingAccessLogId.toString());

    assertThat(parkingAccessLogId).isEqualTo(sameParkingAccessLogId);
    assertThat(parkingAccessLogId.hashCode()).isEqualTo(sameParkingAccessLogId.hashCode());
  }
}
