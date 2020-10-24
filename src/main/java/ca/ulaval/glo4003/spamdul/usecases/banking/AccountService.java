package ca.ulaval.glo4003.spamdul.usecases.banking;

import ca.ulaval.glo4003.spamdul.entity.account.MainAccount;
import ca.ulaval.glo4003.spamdul.entity.account.SustainableMobilityProjectAccount;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class AccountService {

  private MainAccount mainAccount;
  private SustainableMobilityProjectAccount sustainableMobilityProjectAccount;

  public AccountService(MainAccount mainAccount,
                        SustainableMobilityProjectAccount sustainableMobilityProjectAccount) {
    this.mainAccount = mainAccount;
    this.sustainableMobilityProjectAccount = sustainableMobilityProjectAccount;
  }

  private Amount MainPercent = Amount.valueOf(60);
  private Amount MobilityProjectPercent = Amount.valueOf(40);


  public void addTransaction(TransactionDto transactionDto) {
    Amount funds = Amount.valueOf(transactionDto.amount);
    mainAccount.addFunds(funds.multiply(MainPercent));
    sustainableMobilityProjectAccount.addFunds(funds.multiply(MobilityProjectPercent));
  }

  public void fundInitiative(Amount amount) {
    sustainableMobilityProjectAccount.withdrawFunds(amount);
  }

}
