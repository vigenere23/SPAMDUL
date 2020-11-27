package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InfractionBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.INFRACTION;

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;

  public InfractionBankAccount(MainBankAccount mainBankAccount,
                               SustainabilityBankAccount sustainabilityBankAccount) {
    this.mainBankAccount = mainBankAccount;
    this.sustainabilityBankAccount = sustainabilityBankAccount;
  }

  public void addRevenue(Amount amount) {
    mainBankAccount.addRevenue(amount.multiply(0.6), TRANSACTION_TYPE);
    sustainabilityBankAccount.addRevenue(amount.multiply(0.4), TRANSACTION_TYPE);
  }

  public Amount getRevenueForSustainability(TransactionFilter transactionFilter) {
    return sustainabilityBankAccount.getRevenue().with(TRANSACTION_TYPE, transactionFilter);
  }
}
