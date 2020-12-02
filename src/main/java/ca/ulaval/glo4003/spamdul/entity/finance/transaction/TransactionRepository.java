package ca.ulaval.glo4003.spamdul.entity.finance.transaction;

import java.util.List;

public interface TransactionRepository {

  List<Transaction> findAll();

  List<Transaction> findAll(TransactionFilter transactionFilter);

  List<Transaction> findAllBy(TransactionType transactionType);

  List<Transaction> findAllBy(TransactionType transactionType, TransactionFilter transactionFilter);

  void save(Transaction transaction);
}
