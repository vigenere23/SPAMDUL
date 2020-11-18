package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public class CampusAccess {

  private CampusAccessCode campusAccessCode;
  private User user;
  private Car car;
  private PeriodType periodType;
  private TimePeriod timePeriod;
  private PassCode associatedPassCode;

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

  public boolean isAccessGranted(LocalDateTime dateOfAccess) {
    return timePeriod.includes(dateOfAccess);
  }

  public void associatePass(PassCode passCode, TimePeriod passTimePeriod) {
    if (associatedPassCode != null) {
      throw new PassAlreadyAssociatedException("This user already has a pass for this date.");
    }
    if (!passTimePeriod.includedIn(timePeriod)) {
      throw new PassNotAcceptedByAccessException(
          "This user does not have campus access for the dates covered by this pass."
      );
    }

    associatedPassCode = passCode;
  }

  public boolean validateAssociatedLicensePlate(LicensePlate licensePlate) {
    return car.validateLicensePlate(licensePlate);
  }

  public PassCode getAssociatedPassCode() {
    return associatedPassCode;
  }

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
        Objects.equals(associatedPassCode, that.associatedPassCode);
  }

  public int hashCode() {
    return Objects.hash(campusAccessCode, user, car, timePeriod, associatedPassCode);
  }

  public User getUser() {
    return user;
  }

  public Car getCar() {
    return car;
  }

  // TODO: should receive PassService instead
  public ParkingZone getParkingZone(PassRepository passRepository) {
    if (associatedPassCode != null) {
      return passRepository.findByPassCode(associatedPassCode)
                           .getParkingZone();
    }

    switch (periodType) {
      case HOURLY:
      case SINGLE_DAY:
        return ParkingZone.ALL;
      default:
        return ParkingZone.FREE;
    }

  }
}
