package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InfractionTransactionService {

  private final TransactionType TRANSACTION_TYPE = TransactionType.INFRACTION;

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;

  public InfractionTransactionService(MainBankAccount mainBankAccount,
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
