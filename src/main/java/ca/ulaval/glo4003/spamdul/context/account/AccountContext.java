package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.MainAccount;
import ca.ulaval.glo4003.spamdul.entity.account.SustainableMobilityProjectAccount;
import ca.ulaval.glo4003.spamdul.usecases.banking.AccountService;

public class AccountContext {

  private final AccountService accountService;


  public AccountContext() {
    MainAccount mainAccount = new MainAccount();
    SustainableMobilityProjectAccount sustainableMobilityProjectAccount = new SustainableMobilityProjectAccount();

    this.accountService = new AccountService(mainAccount, sustainableMobilityProjectAccount);
  }

  public AccountService getAccountService() {
    return accountService;
  }
}
