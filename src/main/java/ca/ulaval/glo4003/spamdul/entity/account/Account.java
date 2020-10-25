package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.utils.Amount;

public class Account {

  private Amount funds = Amount.valueOf(0);

  public Account() {

  }

  public void addFunds(Amount amount) {
    funds = funds.add(amount);
  }

  public void withdrawFunds(Amount amount) {
    if (funds.subtract(amount).isNegative()) {
      throw new InsufficientFundsException("Insufficient funds");
    }
    funds = funds.subtract(amount);
  }

  public Amount getFunds() {
    return funds;
  }
}
