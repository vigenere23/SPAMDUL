package ca.ulaval.glo4003.spamdul.entity.initiatives.carboncredits;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.utils.MathUtil;
import org.junit.Test;

public class CarbonCreditsTest {

  private static final double AMOUNT_DIVIDER = 21.81;

  @Test
  public void givenValue_whenCreating_shouldHaveSameValue() {
    double value = 123;
    CarbonCredits carbonCredits = CarbonCredits.valueOf(value);
    assertThat(carbonCredits.asDouble()).isEqualTo(value);
  }

  @Test
  public void givenAmount_whenCreating_shouldHaveDividedValueRoundedToTwoDecimals() {
    double value = 123;
    Amount amount = Amount.valueOf(value);

    CarbonCredits carbonCredits = CarbonCredits.valueOf(amount);

    double expectedValue = MathUtil.round(value / AMOUNT_DIVIDER, 2);
    assertThat(carbonCredits.asDouble()).isEqualTo(expectedValue);
  }
}
