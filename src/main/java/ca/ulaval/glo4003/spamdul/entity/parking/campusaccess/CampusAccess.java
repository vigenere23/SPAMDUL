package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import java.time.LocalDateTime;
import java.util.Objects;

// TODO: make subclasses depending on PeriodType instead of a having it as an attribute
public class CampusAccess {

  private final CampusAccessCode campusAccessCode;
  private final PeriodType periodType;
  protected final TimePeriod timePeriod;
  private Pass associatedPass;


  public CampusAccess(CampusAccessCode campusAccessCode,
                      PeriodType periodType,
                      TimePeriod timePeriod) {
    this.campusAccessCode = campusAccessCode;
    this.periodType = periodType;
    this.timePeriod = timePeriod;
  }

  public TimePeriod getTimePeriod() {
    return timePeriod;
  }

  public CampusAccessCode getCampusAccessCode() {
    return campusAccessCode;
  }

  public boolean grantAccess(LocalDateTime dateOfAccess) {

    return timePeriod.includes(dateOfAccess);
  }

  public void associatePass(Pass pass) {
    if (associatedPass != null) {
      throw new PassAlreadyAssociatedException("This user already has a pass for this date.");
    }
    if (!pass.getTimePeriod().includedIn(timePeriod)) {
      throw new PassNotAcceptedByAccessException(
          "This user does not have campus access for the dates covered by this pass."
      );
    }

    associatedPass = pass;
  }

  public Pass getAssociatedPass() {
    return associatedPass;
  }

  public ParkingZone getParkingZone() {
    if (associatedPass != null) {
      return associatedPass.getParkingZone();
    }

    switch (periodType) {
      case HOURLY:
      case SINGLE_DAY:
        return ParkingZone.ALL;
      default:
        return ParkingZone.FREE;
    }
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CampusAccess that = (CampusAccess) o;
    return Objects.equals(campusAccessCode, that.campusAccessCode) && periodType == that.periodType
        && Objects.equals(timePeriod, that.timePeriod);
  }

  public int hashCode() {
    return Objects.hash(campusAccessCode, periodType, timePeriod);
  }
}
