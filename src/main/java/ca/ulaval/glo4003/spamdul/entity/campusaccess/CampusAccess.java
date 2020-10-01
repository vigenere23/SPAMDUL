package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.DayOfWeek;

public class CampusAccess {

  private CampusAccessCode campusAccessCode;
  private UserId userId;
  private CarId carId;
  private PassCode passCode;
  private DayOfWeek dayOfWeek;
  private Period period;

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

  public boolean isAccessGranted(DayOfWeek accessingDay) {
    return accessingDay == dayOfWeek;
  }

  public PassCode getPassCode() {
    return passCode;
  }

  public void setPassCode(PassCode passCode) {
    this.passCode = passCode;
  }
}
