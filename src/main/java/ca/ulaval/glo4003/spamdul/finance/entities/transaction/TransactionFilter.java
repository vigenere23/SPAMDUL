package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import ca.ulaval.glo4003.spamdul.shared.infrastructure.filter.Filter;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionFilter {

  private final Filter<Transaction> filterContainer = new Filter<>();

  public TransactionFilter setData(List<Transaction> transactions) {
    filterContainer.setData(transactions);

    return this;
  }

  public TransactionFilter betweenDates(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate != null) {
      filterContainer.addCondition(transaction -> !transaction.getCreatedAt().isBefore(startDate));
    }

    if (endDate != null) {
      filterContainer.addCondition(transaction -> !transaction.getCreatedAt().isAfter(endDate));
    }

    return this;
  }

  public List<Transaction> getResults() {
    return filterContainer.getResults();
  }
}
