package ca.ulaval.glo4003.spamdul.context.account;

import ca.ulaval.glo4003.spamdul.entity.account.Account;
import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.account.MainAccount;
import ca.ulaval.glo4003.spamdul.entity.account.SustainableMobilityProjectAccount;
import ca.ulaval.glo4003.spamdul.usecases.banking.BankingService;
import ca.ulaval.glo4003.spamdul.usecases.transactions.TransactionService;
import java.util.ArrayList;
import java.util.List;

public class AccountContext {

  private final BankingService bankingService;
  private final Bank bank;
  private final SustainableMobilityProjectAccount sustainableMobilityProjectAccount;

  public AccountContext(TransactionService transactionService) {
    List<Account> accountList = new ArrayList<>();
    MainAccount mainAccount = new MainAccount();
    accountList.add(mainAccount);
    sustainableMobilityProjectAccount = new SustainableMobilityProjectAccount();
    accountList.add(sustainableMobilityProjectAccount);
    bank = new Bank(accountList);

    this.bankingService = new BankingService(transactionService, bank, sustainableMobilityProjectAccount);
  }

  public BankingService getBankingService() {
    return bankingService;
  }

  public Bank getBank() {
    return bank;
  }

  public SustainableMobilityProjectAccount getSustainableMobilityProjectAccount() {
    return sustainableMobilityProjectAccount;
  }
}
