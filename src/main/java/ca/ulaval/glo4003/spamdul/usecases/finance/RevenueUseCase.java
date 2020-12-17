package ca.ulaval.glo4003.spamdul.usecases.finance;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator.AccessLevelValidator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFilter;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CarbonCreditsTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.initiatives.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.usecases.finance.dto.TransactionQueryDto;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class RevenueUseCase {

  private final AccessLevelValidator accessLevelValidator;
  private final CampusAccessTransactionService campusAccessTransactionService;
  private final InfractionTransactionService infractionTransactionService;
  private final PassTransactionService passTransactionService;
  private final CarbonCreditsTransactionService carbonCreditsTransactionService;

  public RevenueUseCase(AccessLevelValidator accessLevelValidator,
                        CampusAccessTransactionService campusAccessTransactionService,
                        InfractionTransactionService infractionTransactionService,
                        PassTransactionService passTransactionService,
                        CarbonCreditsTransactionService carbonCreditsTransactionService) {
    this.accessLevelValidator = accessLevelValidator;
    this.campusAccessTransactionService = campusAccessTransactionService;
    this.infractionTransactionService = infractionTransactionService;
    this.passTransactionService = passTransactionService;
    this.carbonCreditsTransactionService = carbonCreditsTransactionService;
  }

  public Map<CarType, Amount> getCampusAccessTotalRevenueByCarType(TransactionQueryDto transactionQueryDto,
                                                                   TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Map<CarType, Amount> carTypeRevenues = new EnumMap<>(CarType.class);

    Arrays.stream(CarType.values())
          .forEach(carType -> {
            Amount amount = campusAccessTransactionService.getRevenue(carType, getFilter(transactionQueryDto));
            carTypeRevenues.put(carType, amount);
          });

    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue(TransactionQueryDto transactionQueryDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return infractionTransactionService.getRevenueForSustainability(getFilter(transactionQueryDto));
  }

  public Amount getPassTotalRevenue(TransactionQueryDto transactionQueryDto, TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    return passTransactionService.getRevenue(getFilter(transactionQueryDto));
  }

  public CarbonCredits getAllBoughtCarbonCredit(TemporaryToken temporaryToken) {
    accessLevelValidator.validate(temporaryToken);

    Amount total = carbonCreditsTransactionService.getRevenue();
    return CarbonCredits.valueOf(total);
  }

  private TransactionFilter getFilter(TransactionQueryDto transactionQueryDto) {
    return new TransactionFilter()
        .betweenDates(transactionQueryDto.startDate,
                      transactionQueryDto.endDate);
  }
}
