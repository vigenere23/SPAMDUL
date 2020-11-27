package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class CarbonCreditsBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.CARBON_CREDIT;

  private final TransactionFactory transactionFactory;
  private final TransactionRepository transactionRepository;

  public CarbonCreditsBankAccount(TransactionFactory transactionFactory,
                                  TransactionRepository transactionRepository) {
    this.transactionFactory = transactionFactory;
    this.transactionRepository = transactionRepository;
  }

  public void addRevenue(Amount amount) {
    Transaction revenueTransaction = transactionFactory.create(TRANSACTION_TYPE, amount);
    transactionRepository.save(revenueTransaction);
  }

  public Amount getRevenue() {
    List<Transaction> transactions = transactionRepository.findAll();
    return new TransactionList(transactions).getBalance();
  }
}
