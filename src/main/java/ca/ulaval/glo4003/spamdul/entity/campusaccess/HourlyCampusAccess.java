package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HourlyCampusAccess extends CampusAccess {

  private boolean hasBeenAccessed = false;
  private final BigDecimal numberOfHours;

  public HourlyCampusAccess(CampusAccessCode campusAccessCode,
                            PeriodType periodType,
                            TimePeriod timePeriod,
                            BigDecimal numberOfHours) {
    super(campusAccessCode, periodType, timePeriod);
    this.numberOfHours = numberOfHours;
  }

  @Override
  public boolean grantAccess(LocalDateTime dateOfAccess) {
    boolean accessGranted = super.grantAccess(dateOfAccess);

    if (accessGranted && !hasBeenAccessed) {
      timePeriod.restrainHourlyPeriod(dateOfAccess, numberOfHours);
      hasBeenAccessed = true;
    }

    return accessGranted;
  }
}
