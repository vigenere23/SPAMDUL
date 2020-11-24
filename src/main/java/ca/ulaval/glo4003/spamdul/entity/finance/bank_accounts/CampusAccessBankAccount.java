package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.finance.CampusAccessTransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class CampusAccessBankAccount {

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

    Transaction sustainableTransaction = sustainabilityBankAccount.addRevenue(sustainableAmount,
                                                                              TransactionType.CAMPUS_ACCESS);
    Transaction reminderTransaction = mainBankAccount.addRevenue(remainderAmount, TransactionType.CAMPUS_ACCESS);

    campusAccessTransactionRepository.save(sustainableTransaction, carType);
    campusAccessTransactionRepository.save(reminderTransaction, carType);
  }

  public Amount getBalance(CarType carType) {
    TransactionList transactionList = new TransactionList(campusAccessTransactionRepository.findAllBy(carType));
    return transactionList.getBalance();
  }

  public Amount getBalance() {
    TransactionList transactionList = new TransactionList(campusAccessTransactionRepository.findAll());
    return transactionList.getBalance();
  }
}
