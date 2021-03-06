package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDayOfWeek;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HourlyCampusAccess extends CampusAccess {

  private boolean hasBeenAccessed = false;
  private final BigDecimal numberOfHours;

  public HourlyCampusAccess(CampusAccessCode campusAccessCode,
                            BigDecimal numberOfHours) {
    super(campusAccessCode, null);
    this.numberOfHours = numberOfHours;
  }

  @Override
  public boolean grantAccess(LocalDateTime dateOfAccess) {
    if (!hasBeenAccessed) {
      timePeriod = new TimePeriod(
          dateOfAccess,
          dateOfAccess.plusHours(numberOfHours.longValue()),
          TimePeriodDayOfWeek.ALL);
      hasBeenAccessed = true;
    }

    return super.grantAccess(dateOfAccess);
  }

  @Override
  public ParkingZone getParkingZone() {
    return ParkingZone.ALL;
  }
}
