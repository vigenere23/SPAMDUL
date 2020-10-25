package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.account.Bank;
import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transactions.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionFactory transactionFactory;
  private final BankRepository bankRepository;

  public TransactionService(TransactionRepository transactionRepository,
                            TransactionFactory transactionFactory,
                            BankRepository bankRepository) {
    this.transactionRepository = transactionRepository;
    this.transactionFactory = transactionFactory;
    this.bankRepository = bankRepository;
  }

  public Map<CarType, Amount> getCampusAccessTotalRevenueByCarType(TransactionQueryDto transactionQueryDto) {
    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);
    TransactionFilter transactionFilter = getFilter(transactionQueryDto);

    Arrays.stream(CarType.values())
          .forEach(carType -> {
            List<Transaction> transactions = this.transactionRepository.findAllBy(carType);
            transactions = transactionFilter.setData(transactions).getResults();
            carTypeRevenues.put(carType, getTotalAmount(transactions));
          });
    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue(TransactionQueryDto transactionQueryDto) {
    List<Transaction> transactions = this.transactionRepository.findAllBy(TransactionType.INFRACTION);
    transactions = getFilter(transactionQueryDto).setData(transactions).getResults();
    return getTotalAmount(transactions);
  }

  public Amount getPassTotalRevenue(TransactionQueryDto transactionQueryDto) {
    List<Transaction> transactions = this.transactionRepository.findAllBy(TransactionType.PASS);
    transactions = getFilter(transactionQueryDto).setData(transactions).getResults();
    return getTotalAmount(transactions);
  }

  public void createTransaction(TransactionDto transactionDto) {
    Transaction transaction = transactionFactory.create(transactionDto);
    transactionRepository.save(transaction);
    Bank bank = bankRepository.getBank();
    bank.addFunds(transaction.getAmount());
    bankRepository.saveBank(bank);
  }

  private Amount getTotalAmount(List<Transaction> transactions) {
    return transactions.stream()
                       .map(Transaction::getAmount)
                       .reduce(Amount.valueOf(0), Amount::add);
  }

  private TransactionFilter getFilter(TransactionQueryDto transactionQueryDto) {
    return new TransactionFilter()
        .betweenDates(transactionQueryDto.startDate,
                      transactionQueryDto.endDate);
  }
}
