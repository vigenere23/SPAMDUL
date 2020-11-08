package ca.ulaval.glo4003.spamdul.entity.bank;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import java.util.ArrayList;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;

public class CarbonCreditsBankAccount {

  private final List<Transaction> transactions = new ArrayList<>();

  public void save(Transaction transaction) {
    transactions.add(transaction);
  }

  public List<Transaction> findAll() {
    return Lists.newArrayList(transactions);
  }
}
