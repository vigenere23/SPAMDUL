package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.utils.Amount;

public class MainAccount extends Account {
  //should i do it by a factory?
  Amount percent = Amount.valueOf(60);

  @Override
  public void addFunds(Amount amount) {
    funds.add(amount.multiply(percent));
  }
}
