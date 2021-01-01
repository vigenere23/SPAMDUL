package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class InfractionIdTest {

  @Test
  public void givenValue_whenCreatingInfractionId_shouldCreateWithValue() {
    String value = "1234";
    InfractionId infractionId = InfractionId.valueOf(value);
    assertThat(infractionId.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentInfractionId_shouldNotBeEqual() {
    InfractionId infractionId = InfractionId.valueOf("1234");
    InfractionId anotherInfractionId = InfractionId.valueOf("5678");

    assertThat(infractionId).isNotEqualTo(anotherInfractionId);
    assertThat(infractionId.hashCode()).isNotEqualTo(anotherInfractionId.hashCode());
  }

  @Test
  public void whenComparingTheSameInfractionId_shouldBeEqual() {
    InfractionId infractionId = InfractionId.valueOf("1234");
    InfractionId sameInfractionId = InfractionId.valueOf(infractionId.toString());

    assertThat(infractionId).isEqualTo(sameInfractionId);
    assertThat(infractionId.hashCode()).isEqualTo(sameInfractionId.hashCode());
  }
}
