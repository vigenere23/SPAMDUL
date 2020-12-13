package ca.ulaval.glo4003.spamdul.shared.amount;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class AmountTest {

  @Test(expected = InvalidAmountException.class)
  public void givenNaN_whenCreating_shouldThrowException() {
    Amount.valueOf(Double.NaN);
  }

  @Test(expected = InvalidAmountException.class)
  public void givenNegativeInfiniteValue_whenCreating_shouldThrowException() {
    Amount.valueOf(Double.NEGATIVE_INFINITY);
  }

  @Test(expected = InvalidAmountException.class)
  public void givenPositiveInfiniteValue_whenCreating_shouldThrowException() {
    Amount.valueOf(Double.POSITIVE_INFINITY);
  }

  @Test(expected = InvalidAmountException.class)
  public void givenInvalidStringValue_whenCreating_shouldThrowException() {
    Amount.valueOf("asb");
  }

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
