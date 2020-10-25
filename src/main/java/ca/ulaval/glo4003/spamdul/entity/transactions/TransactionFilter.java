package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.utils.filter.FilterContainer;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionFilter {

  private final FilterContainer<Transaction> filterContainer = new FilterContainer<>();

  public TransactionFilter setData(List<Transaction> transactions) {
    filterContainer.setData(transactions);
    return this;
  }

  public TransactionFilter betweenDates(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate != null) {
      filterContainer.addFilter(transaction -> !transaction.getCreatedAt().isBefore(startDate));
    }
    if (endDate != null) {
      filterContainer.addFilter(transaction -> !transaction.getCreatedAt().isAfter(endDate));
    }

    return this;
  }

  //TODO a tester
  public TransactionFilter by(CarType carType) {
    filterContainer.addFilter(transaction -> {
      if (transaction.getTransactionType().equals(TransactionType.CAMPUS_ACCESS)) {
        return ((CampusAccessTransaction) transaction).getCarType().equals(carType);
      }

      return false;
    });

    return this;
  }

  public List<Transaction> getResults() {
    return filterContainer.getResults();
  }
}
