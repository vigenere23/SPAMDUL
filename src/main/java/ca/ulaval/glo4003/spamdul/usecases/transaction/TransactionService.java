package ca.ulaval.glo4003.spamdul.usecases.transaction;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;

public class TransactionService {

  private TransactionRepository transactionRepository;
  private TransactionFactory transactionFactory;

  public TransactionService(TransactionRepository transactionRepository, TransactionFactory transactionFactory) {
    this.transactionRepository = transactionRepository;
    this.transactionFactory = transactionFactory;
  }

  public void createTransaction(TransactionDto transactionDto) {
    Transaction transaction = transactionFactory.create(transactionDto);
    transactionRepository.save(transaction);
  }
}
