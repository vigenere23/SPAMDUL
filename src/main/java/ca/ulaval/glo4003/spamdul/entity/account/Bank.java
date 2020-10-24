package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.List;

public class Bank {

  List<Account> accountList;

  public Bank(List<Account> accountList) {
    this.accountList = accountList;
  }

  public void addFunds(Amount amount) {
    for (Account account: accountList) {
      account.addFunds(amount);
    }
  }
}
