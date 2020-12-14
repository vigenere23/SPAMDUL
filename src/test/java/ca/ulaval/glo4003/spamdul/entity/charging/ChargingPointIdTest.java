package ca.ulaval.glo4003.spamdul.entity.charging;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class ChargingPointIdTest {

  @Test
  public void givenValue_whenCreatingChargingPointId_shouldCreateWithValue() {
    String value = "1234";
    ChargingPointId chargingPointId = ChargingPointId.valueOf(value);

    assertThat(chargingPointId.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentChargingPointId_shouldNotBeEqual() {
    ChargingPointId chargingPointId = ChargingPointId.valueOf("1234");
    ChargingPointId anotherChargingPointId = ChargingPointId.valueOf("5678");

    assertThat(chargingPointId).isNotEqualTo(anotherChargingPointId);
    assertThat(chargingPointId.hashCode()).isNotEqualTo(anotherChargingPointId.hashCode());
  }

  @Test
  public void whenComparingTheSameChargingPointId_shouldBeEqual() {
    ChargingPointId chargingPointId = ChargingPointId.valueOf("1234");
    ChargingPointId sameChargingPointId = ChargingPointId.valueOf(chargingPointId.toString());

    assertThat(chargingPointId).isEqualTo(sameChargingPointId);
    assertThat(chargingPointId.hashCode()).isEqualTo(sameChargingPointId.hashCode());
  }
}
