package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class CampusAccessBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CampusAccessTransactionRepository campusAccessTransactionRepository;

  public CampusAccessBankAccount(MainBankAccount mainBankAccount,
                                 SustainabilityBankAccount sustainabilityBankAccount,
                                 CampusAccessTransactionRepository campusAccessTransactionRepository) {
    this.mainBankAccount = mainBankAccount;
    this.sustainabilityBankAccount = sustainabilityBankAccount;
    this.campusAccessTransactionRepository = campusAccessTransactionRepository;
  }

  public void addRevenue(Amount amount, CarType carType) {
    Amount sustainableAmount = amount.multiply(0.4);
    Amount remainderAmount = amount.multiply(0.6);

    Transaction sustainableTransaction = sustainabilityBankAccount.addRevenue(sustainableAmount, TRANSACTION_TYPE);
    Transaction reminderTransaction = mainBankAccount.addRevenue(remainderAmount, TRANSACTION_TYPE);

    campusAccessTransactionRepository.save(sustainableTransaction, carType);
    campusAccessTransactionRepository.save(reminderTransaction, carType);
  }

  public Amount getRevenue(CarType carType, TransactionFilter transactionFilter) {
    List<Transaction> transactions = campusAccessTransactionRepository.findAllBy(carType, transactionFilter);

    return new TransactionList(transactions).getBalance();
  }

  public Amount getRevenue() {
    List<Transaction> transactions = campusAccessTransactionRepository.findAll();

    return new TransactionList(transactions).getBalance();
  }
}
