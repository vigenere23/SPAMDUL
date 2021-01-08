package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;

public class AccessPeriodHour extends AccessPeriod {

  private final int numberOfHours;

  public AccessPeriodHour(TimePeriod timePeriod, int numberOfHours) {
    super(timePeriod);
    this.numberOfHours = numberOfHours;
  }

  @Override public String toString() {
    return String.format("access period for %s hour(s)", numberOfHours);
  }

  @Override protected AccessPeriodType getType() {
    return AccessPeriodType.HOUR;
  }

  @Override
  public Amount getParkingZonePrice(ParkingZoneFeeRepository zoneFeeRepository, ParkingZone parkingZone) {
    return zoneFeeRepository.findBy(parkingZone, getType()).multiply(numberOfHours);
  }

  @Override
  public Amount getCarTypePrice(ParkingCarFeeRepository carFeeRepository, CarType carType) {
    return carFeeRepository.findBy(carType, getType()).multiply(numberOfHours);
  }
}
