package ca.ulaval.glo4003.spamdul.entity.rechargul;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class RechargULCardIdTest {

  @Test
  public void givenValue_whenCreatingRechargULCardId_shouldCreateWithValue() {
    String value = "1234";
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(value);
    assertThat(rechargULCardId.toString()).isEqualTo(value);
  }

  @Test
  public void whenComparingDifferentRechargULCardId_shouldNotBeEqual() {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf("1234");
    RechargULCardId anotherRechargULCardId = RechargULCardId.valueOf("5678");

    assertThat(rechargULCardId).isNotEqualTo(anotherRechargULCardId);
    assertThat(rechargULCardId.hashCode()).isNotEqualTo(anotherRechargULCardId.hashCode());
  }

  @Test
  public void whenComparingTheSameRechargULCardId_shouldBeEqual() {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf("1234");
    RechargULCardId sameRechargULCardId = RechargULCardId.valueOf(rechargULCardId.toString());

    assertThat(rechargULCardId).isEqualTo(sameRechargULCardId);
    assertThat(rechargULCardId.hashCode()).isEqualTo(sameRechargULCardId.hashCode());
  }
}
