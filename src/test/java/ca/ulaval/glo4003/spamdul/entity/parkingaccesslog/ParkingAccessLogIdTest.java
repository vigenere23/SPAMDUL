package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class ParkingAccessLogIdTest {

  @Test
  public void whenCreatingParkingAccessLogId_shouldGenerateAnId() {
    ParkingAccessLogId parkingAccessLogId = new ParkingAccessLogId();

    assertThat(parkingAccessLogId.toString()).isNotEqualTo("");
  }

  @Test
  public void whenComparingDifferentParkingAccessLogId_shouldNotBeEqual() {
    ParkingAccessLogId parkingAccessLogId = new ParkingAccessLogId();
    ParkingAccessLogId anotherParkingAccessLogId = new ParkingAccessLogId();

    assertThat(parkingAccessLogId).isNotEqualTo(anotherParkingAccessLogId);
    assertThat(parkingAccessLogId.hashCode()).isNotEqualTo(anotherParkingAccessLogId.hashCode());
  }

  @Test
  public void whenComparingTheSameParkingAccessLogId_shouldBeEqual() {
    ParkingAccessLogId parkingAccessLogId = new ParkingAccessLogId();
    ParkingAccessLogId sameParkingAccessLogId = ParkingAccessLogId.valueOf(parkingAccessLogId.toString());

    assertThat(parkingAccessLogId).isEqualTo(sameParkingAccessLogId);
    assertThat(parkingAccessLogId.hashCode()).isEqualTo(sameParkingAccessLogId.hashCode());
  }
}
