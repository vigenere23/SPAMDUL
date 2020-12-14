package ca.ulaval.glo4003.spamdul.shared.utils;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class MathUtilTest {

  @Test
  public void whenRounding_shouldRound() {
    double rounded = MathUtil.round(1234.5678, 2);
    assertThat(rounded).isEqualTo(1234.57);
  }
}
