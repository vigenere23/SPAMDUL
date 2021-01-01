package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import java.util.List;

public interface TransactionRepository {

  List<Transaction> findAll();

  List<Transaction> findAll(TransactionFilter transactionFilter);

  List<Transaction> findAllBy(TransactionType transactionType);

  List<Transaction> findAllBy(TransactionType transactionType, TransactionFilter transactionFilter);

  void save(Transaction transaction);
}
