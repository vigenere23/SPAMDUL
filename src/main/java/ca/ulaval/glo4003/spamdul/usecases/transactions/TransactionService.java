package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionFactory transactionFactory;
  private final Bank bank;

  public TransactionService(TransactionRepository transactionRepository,
                            TransactionFactory transactionFactory,
                            Bank bank) {
    this.transactionRepository = transactionRepository;
    this.transactionFactory = transactionFactory;
    this.bank = bank;
  }

  public Map<CarType, Amount> getTotalCampusAccessRevenueByCarType() {
    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);
    Arrays.stream(CarType.values())
          .forEach(carType -> carTypeRevenues.put(carType, getTotalAmount(this.transactionRepository.findAllBy(carType))));
    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue() {
    return getTotalAmount(this.transactionRepository.findAllBy(TransactionType.INFRACTION));
  }

  public Amount getPassTotalRevenue() {
    return getTotalAmount(this.transactionRepository.findAllBy(TransactionType.PASS));
  }

  public void createTransaction(TransactionDto transactionDto) {
    Transaction transaction = transactionFactory.create(transactionDto);
    transactionRepository.save(transaction);
    bank.addFunds(transaction.getAmount());
  }

  private Amount getTotalAmount(List<Transaction> transactions) {
    return transactions.stream()
                       .map(Transaction::getAmount)
                       .reduce(Amount.valueOf(0), Amount::add);
  }
}
