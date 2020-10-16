package ca.ulaval.glo4003.spamdul.usecases.transactions;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {

  public Map<CarType, Amount> getTotalCampusAccessRevenueByCarType() {
    //TODO faire la real implementation ceci est du dummy data
    Map<CarType, Amount> carTypeRevenues = new HashMap<>();
    carTypeRevenues.put(CarType.GOURMANDE, new Amount(1.125));
    carTypeRevenues.put(CarType.ECONOMIQUE, new Amount(2.124));
    carTypeRevenues.put(CarType.SUPER_ECONOMIQUE, new Amount(3.455));
    carTypeRevenues.put(CarType.HYBRIDE_ECONOMIQUE, new Amount(4.454));
    carTypeRevenues.put(CarType.SANS_POLLUTION, new Amount(5.1234));

    return carTypeRevenues;
  }

  public Amount getInfractionsTotalRevenue() {
    //TODO faire la real implementation ceci est du dummy data
    return new Amount(12.114);
  }

  public Amount getPassTotalRevenue() {
    //TODO faire la real implementation ceci est du dummy data
    return new Amount(100.115);
  }
}
