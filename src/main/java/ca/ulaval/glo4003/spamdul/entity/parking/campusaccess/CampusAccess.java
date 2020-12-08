package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
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
  private CarParkingPass associatedParkingPass;


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

  public void associatePass(CarParkingPass carParkingPass) {
    if (associatedParkingPass != null) {
      throw new PassAlreadyAssociatedException("This user already has a parkingPass for this date.");
    }
    if (!carParkingPass.getTimePeriod().includedIn(timePeriod)) {
      throw new PassNotAcceptedByAccessException(
          "This user does not have campus access for the dates covered by this parkingPass."
      );
    }

    associatedParkingPass = carParkingPass;
  }

  public CarParkingPass getAssociatedPass() {
    return associatedParkingPass;
  }

  public ParkingZone getParkingZone() {
    if (associatedParkingPass != null) {
      return associatedParkingPass.getParkingZone();
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
