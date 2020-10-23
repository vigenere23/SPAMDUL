package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionFactory transactionFactory;
  private final Calendar calendar;

  public TransactionService(TransactionRepository transactionRepository,
                            TransactionFactory transactionFactory,
                            Calendar calendar) {
    this.transactionRepository = transactionRepository;
    this.transactionFactory = transactionFactory;
    this.calendar = calendar;
  }

  public Map<CarType, Amount> getTotalCampusAccessRevenueByCarType() {
    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);
    TransactionFilter transactionFilter = new TransactionFilter()
        .betweenDates(calendar.getStartOfSchoolYearAtDate(LocalDate.now()),
                      calendar.getEndOfSchoolYearAtDate(LocalDate.now()));

    Arrays.stream(CarType.values())
          .forEach(carType -> {
            List<Transaction> transactions = this.transactionRepository.findAllBy(carType);
            transactions = transactionFilter.setData(transactions).getResults();
            carTypeRevenues.put(carType, getTotalAmount(transactions));
          });
    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue() {
    TransactionFilter transactionFilter = new TransactionFilter()
        .betweenDates(calendar.getStartOfSchoolYearAtDate(LocalDate.now()),
                      calendar.getEndOfSchoolYearAtDate(LocalDate.now()));

    List<Transaction> transactions = this.transactionRepository.findAllBy(TransactionType.INFRACTION);
    transactions = transactionFilter.setData(transactions).getResults();
    return getTotalAmount(transactions);
  }

  public Amount getPassTotalRevenue() {
    TransactionFilter transactionFilter = new TransactionFilter()
        .betweenDates(calendar.getStartOfSchoolYearAtDate(LocalDate.now()),
                      calendar.getEndOfSchoolYearAtDate(LocalDate.now()));

    List<Transaction> transactions = this.transactionRepository.findAllBy(TransactionType.PASS);
    transactions = transactionFilter.setData(transactions).getResults();
    return getTotalAmount(transactions);
  }

  public void createTransaction(TransactionDto transactionDto) {
    Transaction transaction = transactionFactory.create(transactionDto);
    transactionRepository.save(transaction);
  }

  private Amount getTotalAmount(List<Transaction> transactions) {
    return transactions.stream()
                       .map(Transaction::getAmount)
                       .reduce(Amount.valueOf(0), Amount::add);
  }
}
