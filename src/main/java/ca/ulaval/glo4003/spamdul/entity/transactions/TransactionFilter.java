package ca.ulaval.glo4003.spamdul.entity.transactions;

import ca.ulaval.glo4003.spamdul.utils.filter.FilterContainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionFilter {

  private final FilterContainer<Transaction> filterContainer = new FilterContainer<>();

  public TransactionFilter setData(List<Transaction> transactions) {
    filterContainer.setData(transactions);
    return this;
  }

  public TransactionFilter betweenDates(LocalDate startDate, LocalDate endDate) {
    if (startDate != null) {
      filterContainer.addFilter(transaction -> !transaction.getCreatedAt().isBefore(LocalDateTime.from(startDate)));
    }
    if (endDate != null) {
      filterContainer.addFilter(transaction -> !transaction.getCreatedAt().isAfter(LocalDateTime.from(endDate)));
    }

    return this;
  }

  public List<Transaction> getResults() {
    return filterContainer.getResults();
  }
}
