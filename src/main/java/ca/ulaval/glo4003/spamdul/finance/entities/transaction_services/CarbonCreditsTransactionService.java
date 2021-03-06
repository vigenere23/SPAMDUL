package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionList;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;

public class CarbonCreditsTransactionService {

  private final TransactionType TRANSACTION_TYPE = TransactionType.CARBON_CREDIT;

  private final TransactionFactory transactionFactory;
  private final TransactionRepository transactionRepository;

  public CarbonCreditsTransactionService(TransactionFactory transactionFactory,
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
