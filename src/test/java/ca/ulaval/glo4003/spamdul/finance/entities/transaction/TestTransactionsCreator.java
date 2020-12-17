package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.ArrayList;
import java.util.List;

public class TestTransactionsCreator {

  public static List<Transaction> createMultipleMocks(Amount... amounts) {
    List<Transaction> transactions = new ArrayList<>();

    for (Amount amount : amounts) {
      Transaction transaction = mock(Transaction.class);
      when(transaction.getAmount()).thenReturn(amount);
      transactions.add(transaction);
    }

    return transactions;
  }

  public static Transaction createSingleMock(Amount amount) {
    Transaction transaction = mock(Transaction.class);
    when(transaction.getAmount()).thenReturn(amount);

    return transaction;
  }

  public static Transaction createSingleMock(TransactionType transactionType) {
    Transaction transaction = mock(Transaction.class);
    when(transaction.getTransactionType()).thenReturn(transactionType);

    return transaction;
  }
}
