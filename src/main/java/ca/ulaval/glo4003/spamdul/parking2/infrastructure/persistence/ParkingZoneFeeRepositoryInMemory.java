package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.HashMap;
import java.util.Map;

public class ParkingZoneFeeRepositoryInMemory implements ParkingZoneFeeRepository {

  private final Map<ParkingZone, Map<AccessPeriodType, Amount>> fees = new HashMap<>();

  @Override
  public Amount findBy(ParkingZone parkingZone, AccessPeriodType accessPeriodType) {
    Map<AccessPeriodType, Amount> accessPeriodFees = fees.getOrDefault(parkingZone, new HashMap<>());
    return accessPeriodFees.getOrDefault(accessPeriodType, Amount.valueOf(0));
  }

  @Override
  public void save(ParkingZone parkingZone, AccessPeriodType accessPeriodType, Amount amount) {
    Map<AccessPeriodType, Amount> accessPeriodFees = fees.getOrDefault(parkingZone, new HashMap<>());
    accessPeriodFees.put(accessPeriodType, amount);
    fees.put(parkingZone, accessPeriodFees);
  }
}
