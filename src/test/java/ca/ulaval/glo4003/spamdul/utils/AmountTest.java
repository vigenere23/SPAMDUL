package ca.ulaval.glo4003.spamdul.utils;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class AmountTest {

  @Test
  public void givenEqualValues_whenEquals_thenReturnTrue() {
    Amount amount1 = Amount.valueOf(42);
    Amount amount2 = Amount.valueOf(42);

    assertThat(amount1.equals(amount2)).isTrue();
  }

  @Test
  public void whenAdd_thenReturnCorrectSum() {
    Amount amount1 = Amount.valueOf(3);
    Amount amount2 = Amount.valueOf(5);

    Amount sum = amount1.add(amount2);

    assertThat(sum).isEqualTo(Amount.valueOf(8));
  }
}