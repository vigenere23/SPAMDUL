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
    OTHER_FUNDS = Amount.valueOf(200);
  }

  @Test
  public void givenANewMainAccount_shouldBeEmpty() {
    account = new Account();
    Amount funds = account.getFunds();
    assertThat(funds.asDouble()).isEqualTo(0);
  }

  @Test
  public void givenANewMainAccount_whenAddingFunds_shouldHaveSameFunds() {
    account = new Account();

    account.addFunds(FUNDS);

    Amount funds = account.getFunds();
    assertThat(funds.asDouble()).isEqualTo(FUNDS.asDouble());
  }

  @Test
  public void givenTheSameAccount_whenAddingFunds_shouldHaveTheSumFunds() {
    account = new Account();

    account.addFunds(FUNDS);
    account.addFunds(OTHER_FUNDS);

    Amount funds = account.getFunds();
    assertThat(funds.asDouble()).isEqualTo(FUNDS.add(OTHER_FUNDS).asDouble());
  }
}