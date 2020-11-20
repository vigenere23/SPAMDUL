package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public class CampusAccess {

  private final CampusAccessCode campusAccessCode;
  private final User user;
  private final Car car;
  private final PeriodType periodType;
  protected final TimePeriod timePeriod;
  private Pass associatedPass;


  public CampusAccess(CampusAccessCode campusAccessCode,
                      User user,
                      Car car,
                      PeriodType periodType,
                      TimePeriod timePeriod) {
    this.campusAccessCode = campusAccessCode;
    this.user = user;
    this.car = car;
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

    return  timePeriod.includes(dateOfAccess);
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

  public boolean validateAssociatedLicensePlate(LicensePlate licensePlate) {
    return car.validateLicensePlate(licensePlate);
  }

  public Pass getAssociatedPass() {
    return associatedPass;
  }

  public boolean hasAssociatedPass() {
    return associatedPass != null;
  }

  public User getUser() {
    return user;
  }

  public Car getCar() {
    return car;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CampusAccess that = (CampusAccess) o;
    return Objects.equals(campusAccessCode, that.campusAccessCode) &&
        Objects.equals(user, that.user) &&
        Objects.equals(car, that.car) &&
        Objects.equals(timePeriod, that.timePeriod) &&
        Objects.equals(associatedPass, that.associatedPass);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campusAccessCode, user, car, timePeriod, associatedPass);
  }
}
