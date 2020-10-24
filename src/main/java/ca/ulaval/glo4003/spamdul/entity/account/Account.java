package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.utils.Amount;

public class Account {

  private Amount funds = Amount.valueOf(0);
  private double percentOfRevenue;

  public Account(double percentOfRevenue) {
    this.percentOfRevenue = percentOfRevenue;
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

  public double getPercentOfRevenue() {
    return percentOfRevenue;
  }

  public Amount getFunds() {
    return funds;
  }
}
