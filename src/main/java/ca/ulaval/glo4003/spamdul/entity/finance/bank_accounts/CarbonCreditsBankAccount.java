package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class CarbonCreditsBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.CARBON_CREDIT;

  private final InitiativesBankAccount initiativesBankAccount;
  private final TransactionFactory transactionFactory;
  private final TransactionRepository transactionRepository;

  public CarbonCreditsBankAccount(TransactionFactory transactionFactory,
                                  InitiativesBankAccount initiativesBankAccount,
                                  TransactionRepository transactionRepository) {
    this.transactionFactory = transactionFactory;
    this.initiativesBankAccount = initiativesBankAccount;
    this.transactionRepository = transactionRepository;
  }

  public void addRevenue(Amount amount) {
    initiativesBankAccount.addExpense(amount);

    Transaction revenueTransaction = transactionFactory.create(TRANSACTION_TYPE, amount);
    transactionRepository.save(revenueTransaction);
  }

  public Amount getRevenue() {
    List<Transaction> transactions = transactionRepository.findAll();
    return new TransactionList(transactions).getBalance();
  }
}
