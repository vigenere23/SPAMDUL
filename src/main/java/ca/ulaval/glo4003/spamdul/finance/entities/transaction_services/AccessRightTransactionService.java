package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.MainBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionList;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;

public class AccessRightTransactionService {

  private final TransactionType TRANSACTION_TYPE = TransactionType.ACCESS_RIGHT;

  private final MainBankAccount mainBankAccount;
  private final SustainabilityBankAccount sustainabilityBankAccount;
  private final CampusAccessTransactionRepository accessTransactionRepository;

  public AccessRightTransactionService(MainBankAccount mainBankAccount,
                                       SustainabilityBankAccount sustainabilityBankAccount,
                                       CampusAccessTransactionRepository accessTransactionRepository) {
    this.mainBankAccount = mainBankAccount;
    this.sustainabilityBankAccount = sustainabilityBankAccount;
    this.accessTransactionRepository = accessTransactionRepository;
  }

  public void addRevenue(Amount amount, CarType carType) {
    ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType oldCarType = ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType
        .valueOf(carType.toString());
    Amount sustainableAmount = amount.multiply(0.4);
    Amount remainderAmount = amount.multiply(0.6);

    Transaction sustainableTransaction = sustainabilityBankAccount.addRevenue(sustainableAmount, TRANSACTION_TYPE);
    Transaction reminderTransaction = mainBankAccount.addRevenue(remainderAmount, TRANSACTION_TYPE);

    accessTransactionRepository.save(sustainableTransaction, oldCarType);
    accessTransactionRepository.save(reminderTransaction, oldCarType);
  }

  public Amount getRevenue(CarType carType, TransactionFilter transactionFilter) {
    ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType oldCarType = ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType
        .valueOf(carType.toString());
    List<Transaction> transactions = accessTransactionRepository.findAllBy(oldCarType, transactionFilter);

    return new TransactionList(transactions).getBalance();
  }

  public Amount getRevenue() {
    List<Transaction> transactions = accessTransactionRepository.findAll();

    return new TransactionList(transactions).getBalance();
  }
}
