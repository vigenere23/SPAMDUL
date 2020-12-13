package ca.ulaval.glo4003.spamdul.entity.initiatives.carboncredits;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import java.text.DecimalFormat;
import org.junit.Test;

public class CarbonCreditsTest {

  private static final double AMOUNT_DIVIDER = 21.81;
  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

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

    double expectedValue = Double.parseDouble(decimalFormat.format(value / AMOUNT_DIVIDER));
    assertThat(carbonCredits.asDouble()).isEqualTo(expectedValue);
  }
}
