package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionList;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;

public class CampusAccessTransactionService {

  private final TransactionType TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CampusAccessTransactionRepository campusAccessTransactionRepository;

  public CampusAccessTransactionService(MainBankAccount mainBankAccount,
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
