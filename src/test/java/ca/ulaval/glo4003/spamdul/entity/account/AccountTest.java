package ca.ulaval.glo4003.spamdul.entity.account;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

  private Account account;
  private Amount FUNDS;
  private Amount OTHER_FUNDS;

  @Before
  public void setup() {
    FUNDS = Amount.valueOf(100);
    OTHER_FUNDS = Amount.valueOf(250);
  }
}