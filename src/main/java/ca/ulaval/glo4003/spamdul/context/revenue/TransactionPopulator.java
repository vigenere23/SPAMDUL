package ca.ulaval.glo4003.spamdul.context.revenue;

import ca.ulaval.glo4003.spamdul.entity.account.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TransactionPopulator {

  private final TransactionFactory transactionFactory;
  private final BankRepository bankRepository;

  public TransactionPopulator(TransactionFactory transactionFactory,
                              BankRepository bankRepository) {
    this.transactionFactory = transactionFactory;
    this.bankRepository = bankRepository;
  }

  public void populate(int numberOfRecords) {
    Random random = new Random();
    int MAX_AMOUNT = 300;

    List<TransactionType> allTransactionTypes = Arrays.asList(TransactionType.values());
    List<CarType> allCarTypes = Arrays.asList(CarType.values());

    for (int recordNumber = 0; recordNumber < numberOfRecords; recordNumber++) {
      Transaction transaction = createTransaction(random, allTransactionTypes, allCarTypes, MAX_AMOUNT);
      bankRepository.getMainBankAccount().addTransaction(transaction);
    }
  }

  private Transaction createTransaction(Random random,
                                        List<TransactionType> allTransactionTypes,
                                        List<CarType> allCarTypes,
                                        int maxAmount) {
    TransactionDto dto = new TransactionDto();
    dto.transactionType = allTransactionTypes.get(random.nextInt(allTransactionTypes.size()));
    dto.amount = Math.abs(random.nextDouble() * random.nextInt(maxAmount));
    dto.carType = allCarTypes.get(random.nextInt(allCarTypes.size()));

    return transactionFactory.create(dto);
  }
}
