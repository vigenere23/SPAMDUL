package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.Map;

public interface TransactionService {

  Amount getCampusAccessTotalRevenue();

  Map<CarType, Amount> getTotalCampusAccessRevenueByCarType();

  Amount getInfractionsTotalRevenue();

  Amount getPassTotalRevenue();
}
