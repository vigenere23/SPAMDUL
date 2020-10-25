package ca.ulaval.glo4003.spamdul.entity.account;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class BankTest {

  private Bank bank;
  private Amount FUNDS;
  private double PERCENT_OF_REVENUE = 0.4;
  private double OTHER_PERCENT_OF_REVENUE = 0.6;

  @Mock
  private Account account1 = mock(Account.class);
  @Mock
  private Account account2 = mock(Account.class);


  @Before
  public void setUp() throws Exception {
    FUNDS = Amount.valueOf(1000);
    Map<Account, Double> accountRatioMap = new HashMap<>();
    accountRatioMap.put(account1, PERCENT_OF_REVENUE);
    accountRatioMap.put(account2, OTHER_PERCENT_OF_REVENUE);
    bank = new Bank(accountRatioMap);
  }

  @Test
  public void givenABankWithMultipleAccount_WhenAddingFunds_shouldShoulsCallAddFundOfEachAccount() {
    bank.addFunds(FUNDS);
    verify(account1).addFunds(any());
    verify(account2).addFunds(any());
  }

  @Test
  public void givenABankWithMultipleAccount_WhenAddingFunds_shouldShoulsCallGiveRightPercentFundForEachAccount() {
    bank.addFunds(FUNDS);

    verify(account1).addFunds(FUNDS.multiply(PERCENT_OF_REVENUE));
    verify(account2).addFunds(FUNDS.multiply(OTHER_PERCENT_OF_REVENUE));
  }
}