package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSaleNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CampusAccess {

  private CampusAccessCode campusAccessCode;
  private UserId userId;
  private CarId carId;
  private DayOfWeek dayOfWeek;
  private Period period;
  private PassCode associatedPassCode;

  private LocalDate beginDate = LocalDate.of(2020, 9, 1);
  private LocalDate endDate = LocalDate.of(2020, 12, 31);

  public CampusAccess(CampusAccessCode campusAccessCode,
                      UserId userId,
                      CarId carId,
                      DayOfWeek dayOfWeek,
                      Period period) {
    this.campusAccessCode = campusAccessCode;
    this.userId = userId;
    this.carId = carId;
    this.dayOfWeek = dayOfWeek;
    this.period = period;
  }

  public UserId getUserId() {
    return userId;
  }

  public CarId getCarId() {
    return carId;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public Period getPeriod() {
    return period;
  }

  public CampusAccessCode getCampusAccessCode() {
    return campusAccessCode;
  }

  public boolean isAccessGranted(LocalDate dateOfAccess) {
    if (dateOfAccess.isAfter(endDate) || dateOfAccess.isBefore(beginDate)) {
      return false;
    }

    return dateOfAccess.getDayOfWeek() == dayOfWeek;
  }

  public void associatePass(PassCode passCode, PassType passType, DayOfWeek dayOfWeek) {
    if (associatedPassCode != null) {
      throw new PassAlreadyAssociatedException("This user already has a pass for this date.");
    }
    if (period == Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER) {
      if (passType != PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER || dayOfWeek != this.dayOfWeek) {
        throw new PassSaleNotAcceptedByAccessException(
                "This user does not have campus access for the dates covered by this pass."
        );
      }
    }

    associatedPassCode = passCode;
  }

  public PassCode getAssociatedPassCode() {
    return associatedPassCode;
  }

  public void setAssociatedPassCode(PassCode associatedPassCode) {
    this.associatedPassCode = associatedPassCode;
  }
}
