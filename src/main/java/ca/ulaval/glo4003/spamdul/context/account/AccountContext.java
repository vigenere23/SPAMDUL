package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountContext {

  private final Bank bank;
  private final Account sustainableMobilityProjectAccount;

  public AccountContext() {
    Map<Account, Double> accountRatioMap = new HashMap<>();
    Account mainAccount = new Account();
    accountRatioMap.put(mainAccount, 0.6);
    sustainableMobilityProjectAccount = new Account();
    accountRatioMap.put(sustainableMobilityProjectAccount, 0.4);
    bank = new Bank(accountRatioMap);
  }

  public Bank getBank() {
    return bank;
  }

  public Account getSustainableMobilityProjectAccount() {
    return sustainableMobilityProjectAccount;
  }
}
