package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public abstract class Account {

//  private List<Transaction> transactionList;
  private Amount funds = Amount.valueOf(0);




//  public void addTransaction(Transaction transaction) {
//
//  }
  public void addFunds(Amount amount) {
    funds.add(amount);
  }

  public void withdrawFunds(Amount amount) {
    if (funds.subtract(amount).isNegative()) {
      throw new InvalidInitiativeAmount("insufficient funds");
    }
    funds = funds.subtract(amount);
  }

  public Amount getTotal() {
    return funds;
  }
}
