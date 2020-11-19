package ca.ulaval.glo4003.spamdul.entity.transactions;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionFilterTest {

  private TransactionFilter transactionFilter;
  private final LocalDateTime START_DATE = LocalDateTime.of(LocalDate.of(2010, 1, 1), LocalTime.NOON);
  private final LocalDateTime END_DATE = START_DATE.plusDays(28);
  private final LocalDateTime BETWEEN_DATE = START_DATE.plusDays(15);
  private final LocalDateTime BEFORE_DATE = START_DATE.minusDays(1);
  private final LocalDateTime AFTER_DATE = END_DATE.plusDays(1);
  private final Amount AN_AMOUNT = Amount.valueOf(123.43);
  private final TransactionType A_TRANSACTION_TYPE = TransactionType.CAMPUS_ACCESS;
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final CarType ANOTHER_CAR_TYPE = CarType.HYBRIDE_ECONOMIQUE;

  private final Transaction A_TRANSACTION = new Transaction(AN_AMOUNT, LocalDateTime.now(), A_TRANSACTION_TYPE);
  private final Transaction A_TRANSACTION_COPY = new Transaction(AN_AMOUNT, LocalDateTime.now(), A_TRANSACTION_TYPE);

  @Before
  public void setUp() {
    transactionFilter = new TransactionFilter();
  }

  @Test
  public void givenDataSetAndNoFiltersAdded_whenGettingResults_shouldReturnDataSet() {
    List<Transaction> transactions = Arrays.asList(A_TRANSACTION, A_TRANSACTION_COPY);
    transactionFilter.setData(transactions);

    List<Transaction> filteredTransactions = transactionFilter.getResults();

    assertThat(filteredTransactions).containsExactlyElementsIn(transactions);
  }

  @Test
  public void givenResultsGet_whenGettingResultsASecondTime_shouldReturnSameResults() {
    List<Transaction> transactions = Arrays.asList(A_TRANSACTION, A_TRANSACTION_COPY);
    transactionFilter.setData(transactions);
    List<Transaction> filteredTransactionsFirstTime = transactionFilter.getResults();

    List<Transaction> filteredTransactionsSecondTime = transactionFilter.getResults();

    assertThat(filteredTransactionsSecondTime).containsExactlyElementsIn(filteredTransactionsFirstTime);
  }

  @Test
  public void givenTransactionTooEarly_whenFilteringBetweenDates_shouldReturnEmptyList() {
    Transaction transactionTooEarly = createLogAtDate(BEFORE_DATE);
    transactionFilter.setData(Collections.singletonList(transactionTooEarly));
    List<Transaction> filteredTransactions = transactionFilter.betweenDates(START_DATE, END_DATE).getResults();
    assertThat(filteredTransactions).isEmpty();
  }

  @Test
  public void givenTransactionTooLate_whenFilteringBetweenDates_shouldReturnEmptyList() {
    Transaction transactionTooEarly = createLogAtDate(AFTER_DATE);
    transactionFilter.setData(Collections.singletonList(transactionTooEarly));
    List<Transaction> filteredTransactions = transactionFilter.betweenDates(START_DATE, END_DATE).getResults();
    assertThat(filteredTransactions).isEmpty();
  }

  @Test
  public void givenTransactionBetweenDates_whenFilteringBetweenDates_shouldReturnTheGivenLog() {
    Transaction transactionTooEarly = createLogAtDate(BETWEEN_DATE);
    transactionFilter.setData(Collections.singletonList(transactionTooEarly));
    List<Transaction> filteredTransactions = transactionFilter.betweenDates(START_DATE, END_DATE).getResults();
    assertThat(filteredTransactions).containsExactly(transactionTooEarly);
  }

  @Test
  public void givenTransactionOfTypeDifferentThatCampusAccess_whenFilteringByCarType_shouldReturnEmptyList() {
    Transaction otherThatCampusAccess = createOfType(TransactionType.INFRACTION);
    transactionFilter.setData(Collections.singletonList(otherThatCampusAccess));
    List<Transaction> filteredTransactions = transactionFilter.by(A_CAR_TYPE).getResults();
    assertThat(filteredTransactions).isEmpty();
  }

  @Test
  public void givenTransactionOfTypeCampusAccessWithCarType_whenFilteringByDifferentCarType_shouldReturnEmptyList() {
    Transaction campusAccessTransaction = createForCampusAccess(A_CAR_TYPE);
    transactionFilter.setData(Collections.singletonList(campusAccessTransaction));
    List<Transaction> filteredTransactions = transactionFilter.by(ANOTHER_CAR_TYPE).getResults();
    assertThat(filteredTransactions).isEmpty();
  }

  @Test
  public void givenTransactionOfTypeCampusAccessWithCarType_whenFilteringBySameCarType_shouldReturnThatTransaction() {
    Transaction campusAccessTransaction = createForCampusAccess(A_CAR_TYPE);
    transactionFilter.setData(Collections.singletonList(campusAccessTransaction));
    List<Transaction> filteredTransactions = transactionFilter.by(A_CAR_TYPE).getResults();
    assertThat(filteredTransactions).containsExactly(campusAccessTransaction);
  }

  private Transaction createLogAtDate(LocalDateTime date) {
    return new Transaction(AN_AMOUNT, date, A_TRANSACTION_TYPE);
  }

  private Transaction createOfType(TransactionType type) {
    return new Transaction(AN_AMOUNT, LocalDateTime.now(), type);
  }

  private Transaction createForCampusAccess(CarType carType) {
    return new CampusAccessTransaction(AN_AMOUNT, LocalDateTime.now(), carType);
  }
}
