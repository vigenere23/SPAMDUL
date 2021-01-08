package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingCarFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.ParkingZoneFeeRepository;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessDateException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessTimeException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions.InvalidDateException;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions.InvalidDayOfWeekException;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions.InvalidTimeException;
import java.time.LocalDateTime;

public abstract class AccessPeriod {

  protected final TimePeriod timePeriod;

  protected AccessPeriod(TimePeriod timePeriod) {
    this.timePeriod = timePeriod;
  }

  public void validateAccess(LocalDateTime accessDateTime) {
    try {
      timePeriod.validateDateTime(accessDateTime);
    } catch (InvalidDateException | InvalidDayOfWeekException exception) {
      throw new InvalidAccessDateException(accessDateTime.toLocalDate());
    } catch (InvalidTimeException exception) {
      throw new InvalidAccessTimeException(accessDateTime.toLocalTime());
    }
  }

  @Override
  public abstract String toString();

  protected abstract AccessPeriodType getType();

  public Amount getParkingZonePrice(ParkingZoneFeeRepository zoneFeeRepository, ParkingZone parkingZone) {
    return zoneFeeRepository.findBy(parkingZone, getType());
  }

  public Amount getCarTypePrice(ParkingCarFeeRepository carFeeRepository, CarType carType) {
    return carFeeRepository.findBy(carType, getType());
  }

  public TimePeriod getTimePeriod() {
    return timePeriod;
  }
}
