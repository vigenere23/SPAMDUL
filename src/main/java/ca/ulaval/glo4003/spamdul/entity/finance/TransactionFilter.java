package ca.ulaval.glo4003.spamdul.entity.finance;

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

  public List<Transaction> getResults() {
    return filterContainer.getResults();
  }
}
