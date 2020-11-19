package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.bank.BankRepository;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.usecases.transactions.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TransactionService {

  private final BankRepository bankRepository;
  private AccessLevelValidator accessLevelValidator;

  public TransactionService(BankRepository bankRepository, AccessLevelValidator accessLevelValidator) {
    this.bankRepository = bankRepository;
    this.accessLevelValidator = accessLevelValidator;
  }

  public Map<CarType, Amount> getCampusAccessTotalRevenueByCarType(TransactionQueryDto transactionQueryDto,
                                                                   TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);
    List<Transaction> transactions = bankRepository.getMainBankAccount().findAllBy(TransactionType.CAMPUS_ACCESS);

    Arrays.stream(CarType.values())
          .forEach(carType -> {
            List<Transaction> filteredTransactions = getFilter(transactionQueryDto).by(carType)
                                                                                   .setData(transactions)
                                                                                   .getResults();
            carTypeRevenues.put(carType, getTotalAmount(filteredTransactions));
          });

    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue(TransactionQueryDto transactionQueryDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    List<Transaction> transactions = this.bankRepository.getMainBankAccount().findAllBy(TransactionType.INFRACTION);
    transactions = getFilter(transactionQueryDto).setData(transactions).getResults();

    return getTotalAmount(transactions);
  }

  public Amount getPassTotalRevenue(TransactionQueryDto transactionQueryDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    List<Transaction> transactions = this.bankRepository.getMainBankAccount().findAllBy(TransactionType.PASS);
    transactions = getFilter(transactionQueryDto).setData(transactions).getResults();

    return getTotalAmount(transactions);
  }

  public CarbonCredits getAllBoughtCarbonCredit(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    List<Transaction> transactions = this.bankRepository.getCarbonCreditsBankAccount().findAll();
    return CarbonCredits.valueOf(getTotalAmount(transactions));
  }

  private Amount getTotalAmount(List<Transaction> transactions) {
    return transactions.stream()
                       .map(Transaction::getAmount)
                       .reduce(Amount.valueOf(0), Amount::add);
  }

  private TransactionFilter getFilter(TransactionQueryDto transactionQueryDto) {
    return new TransactionFilter()
        .betweenDates(transactionQueryDto.startDate,
                      transactionQueryDto.endDate);
  }
}
