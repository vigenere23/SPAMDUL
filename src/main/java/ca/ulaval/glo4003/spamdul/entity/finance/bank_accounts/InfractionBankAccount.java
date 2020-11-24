package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

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

  public Amount getSustainabilityAccountBalance() {
    List<Transaction> transactions = sustainabilityBankAccount.getTransactionsOfType(TRANSACTION_TYPE);
    TransactionList transactionList = new TransactionList(transactions);
    return transactionList.getBalance();
  }

  public Amount getMainAccountBalance() {
    List<Transaction> transactions = sustainabilityBankAccount.getTransactionsOfType(TRANSACTION_TYPE);
    TransactionList transactionList = new TransactionList(transactions);
    return transactionList.getBalance();
  }

  public Amount getBalance() {
    return getSustainabilityAccountBalance().add(getMainAccountBalance());
  }
}
