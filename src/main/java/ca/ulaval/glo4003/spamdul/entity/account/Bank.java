package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;

public class Bank {

  Map<Account, Double> accountRatioMap;

  public Bank(Map<Account, Double> accountRatioMap) {
    this.accountRatioMap = accountRatioMap;
  }

  public void addFunds(Amount amount) {
    for (Map.Entry<Account, Double> accountRatio : accountRatioMap.entrySet()) {
      accountRatio.getKey().addFunds(amount.multiply(accountRatio.getValue()));
    }
  }
}
