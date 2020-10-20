package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionDto;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionRepository;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.EnumMap;
import java.util.Map;

public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionFactory transactionFactory;

  public TransactionService(TransactionRepository transactionRepository,
                            TransactionFactory transactionFactory) {
    this.transactionRepository = transactionRepository;
    this.transactionFactory = transactionFactory;
  }

  public Map<CarType, Amount> getTotalCampusAccessRevenueByCarType() {
    //TODO faire la real implementation ceci est du dummy data
    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);
    carTypeRevenues.put(CarType.GOURMANDE, Amount.valueOf(1.125));
    carTypeRevenues.put(CarType.ECONOMIQUE, Amount.valueOf(2.124));
    carTypeRevenues.put(CarType.SUPER_ECONOMIQUE, Amount.valueOf(3.455));
    carTypeRevenues.put(CarType.HYBRIDE_ECONOMIQUE, Amount.valueOf(4.454));
    carTypeRevenues.put(CarType.SANS_POLLUTION, Amount.valueOf(5.1234));

    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue() {
    //TODO faire la real implementation ceci est du dummy data
    return Amount.valueOf(12.114);
  }

  public Amount getPassTotalRevenue() {
    //TODO faire la real implementation ceci est du dummy data
    return Amount.valueOf(100.115);
  }

  public void createTransaction(TransactionDto transactionDto) {
    Transaction transaction = transactionFactory.create(transactionDto);
    transactionRepository.save(transaction);
  }
}
