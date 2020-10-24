package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import java.util.ArrayList;
import java.util.List;

public class AccountContext {

  private final Bank bank;
  private final Account sustainableMobilityProjectAccount;

  public AccountContext() {
    List<Account> accountList = new ArrayList<>();
    Account mainAccount = new Account(0.6);
    accountList.add(mainAccount);
    sustainableMobilityProjectAccount = new Account(0.4);
    accountList.add(sustainableMobilityProjectAccount);
    bank = new Bank(accountList);
  }

  public Bank getBank() {
    return bank;
  }

  public Account getSustainableMobilityProjectAccount() {
    return sustainableMobilityProjectAccount;
  }
}
