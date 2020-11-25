package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class PassBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.PASS;

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;

  public PassBankAccount(MainBankAccount mainBankAccount,
                         SustainabilityBankAccount sustainabilityBankAccount) {
    this.mainBankAccount = mainBankAccount;
    this.sustainabilityBankAccount = sustainabilityBankAccount;
  }

  public void addRevenue(Amount amount) {
    mainBankAccount.addRevenue(amount.multiply(0.6), TRANSACTION_TYPE);
    sustainabilityBankAccount.addRevenue(amount.multiply(0.4), TRANSACTION_TYPE);
  }

  public Amount getRevenueForSustainability() {
    return sustainabilityBankAccount.getRevenue(TRANSACTION_TYPE);
  }

  public Amount getRevenueForSustainability(TransactionFilter transactionFilter) {
    return sustainabilityBankAccount.getRevenue(TRANSACTION_TYPE, transactionFilter);
  }

  public Amount getRevenueForMainBankAccount() {
    return mainBankAccount.getRevenue(TRANSACTION_TYPE);
  }

  public Amount getRevenueForMainBankAccount(TransactionFilter transactionFilter) {
    return mainBankAccount.getRevenue(TRANSACTION_TYPE, transactionFilter);
  }

  public Amount getRevenue() {
    return getRevenueForMainBankAccount().add(getRevenueForSustainability());
  }

  public Amount getRevenue(TransactionFilter transactionFilter) {
    return getRevenueForMainBankAccount(transactionFilter).add(getRevenueForSustainability(transactionFilter));
  }
}
