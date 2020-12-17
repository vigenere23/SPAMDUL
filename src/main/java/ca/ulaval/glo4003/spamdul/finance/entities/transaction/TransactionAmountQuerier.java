package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class TransactionAmountQuerier {

  private final TransactionRepository transactionRepository;

  public TransactionAmountQuerier(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Amount total() {
    return new TransactionList(transactionRepository.findAll()).getBalance();
  }

  public Amount with(TransactionFilter transactionFilter) {
    return new TransactionList(transactionRepository.findAll(transactionFilter)).getBalance();
  }

  public Amount with(TransactionType transactionType) {
    return new TransactionList(transactionRepository.findAllBy(transactionType)).getBalance();
  }

  public Amount with(TransactionType transactionType, TransactionFilter transactionFilter) {
    return new TransactionList(transactionRepository.findAllBy(transactionType, transactionFilter)).getBalance();
  }
}
