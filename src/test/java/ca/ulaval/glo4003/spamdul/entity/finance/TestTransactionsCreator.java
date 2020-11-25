package ca.ulaval.glo4003.spamdul.entity.finance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.ArrayList;
import java.util.List;

public class TestTransactionsCreator {

  public static List<Transaction> createTransactions(Amount... amounts) {
    List<Transaction> transactions = new ArrayList<>();

    for (Amount amount : amounts) {
      Transaction transaction = mock(Transaction.class);
      when(transaction.getAmount()).thenReturn(amount);
      transactions.add(transaction);
    }

    return transactions;
  }
}
