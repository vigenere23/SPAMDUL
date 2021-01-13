package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.HashMap;
import java.util.Map;

public class ParkingCarFeeRepositoryInMemory implements ParkingCarFeeRepository {

  private final Map<CarType, Map<AccessPeriodType, Amount>> fees = new HashMap<>();

  @Override
  public Amount findBy(CarType carType, AccessPeriodType accessPeriodType) {
    Map<AccessPeriodType, Amount> accessPeriodFees = fees.getOrDefault(carType, new HashMap<>());
    return accessPeriodFees.getOrDefault(accessPeriodType, Amount.valueOf(0));
  }

  @Override
  public void save(CarType carType, AccessPeriodType accessPeriodType, Amount amount) {
    Map<AccessPeriodType, Amount> accessPeriodFees = fees.getOrDefault(carType, new HashMap<>());
    accessPeriodFees.put(accessPeriodType, amount);
    fees.put(carType, accessPeriodFees);
  }
}
