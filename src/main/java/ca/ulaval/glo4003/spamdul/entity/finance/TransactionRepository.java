package ca.ulaval.glo4003.spamdul.entity.finance;

import java.util.List;

public interface TransactionRepository {

  List<Transaction> findAll();

  List<Transaction> findAllBy(TransactionType transactionType);

  void save(Transaction transaction);
}
