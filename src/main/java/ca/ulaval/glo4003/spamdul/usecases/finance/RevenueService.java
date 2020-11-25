package ca.ulaval.glo4003.spamdul.usecases.finance;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CampusAccessBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.CarbonCreditsBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InfractionBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.PassBankAccount;
import ca.ulaval.glo4003.spamdul.usecases.finance.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class RevenueService {

  private final AccessLevelValidator accessLevelValidator;
  private final CampusAccessBankAccount campusAccessBankAccount;
  private final InfractionBankAccount infractionBankAccount;
  private final PassBankAccount passBankAccount;
  private final CarbonCreditsBankAccount carbonCreditsBankAccount;

  public RevenueService(AccessLevelValidator accessLevelValidator,
                        CampusAccessBankAccount campusAccessBankAccount,
                        InfractionBankAccount infractionBankAccount,
                        PassBankAccount passBankAccount,
                        CarbonCreditsBankAccount carbonCreditsBankAccount) {
    this.accessLevelValidator = accessLevelValidator;
    this.campusAccessBankAccount = campusAccessBankAccount;
    this.infractionBankAccount = infractionBankAccount;
    this.passBankAccount = passBankAccount;
    this.carbonCreditsBankAccount = carbonCreditsBankAccount;
  }

  public Map<CarType, Amount> getCampusAccessTotalRevenueByCarType(TransactionQueryDto transactionQueryDto,
                                                                   TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);

    Arrays.stream(CarType.values())
          .forEach(carType -> {
            Amount amount = campusAccessBankAccount.getRevenue(carType, getFilter(transactionQueryDto));
            carTypeRevenues.put(carType, amount);
          });

    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue(TransactionQueryDto transactionQueryDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return infractionBankAccount.getRevenueForSustainability(getFilter(transactionQueryDto));
  }

  public Amount getPassTotalRevenue(TransactionQueryDto transactionQueryDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return passBankAccount.getRevenue(getFilter(transactionQueryDto));
  }

  public CarbonCredits getAllBoughtCarbonCredit(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Amount total = carbonCreditsBankAccount.getRevenue();
    return CarbonCredits.valueOf(total);
  }

  private TransactionFilter getFilter(TransactionQueryDto transactionQueryDto) {
    return new TransactionFilter()
        .betweenDates(transactionQueryDto.startDate,
                      transactionQueryDto.endDate);
  }
}
