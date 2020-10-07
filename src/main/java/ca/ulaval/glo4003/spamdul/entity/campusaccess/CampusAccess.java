package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSaleNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class CampusAccess {

  private CampusAccessCode campusAccessCode;
  private UserId userId;
  private CarId carId;
  private TimePeriod timePeriod;
  private PassCode associatedPassCode;

  public CampusAccess(CampusAccessCode campusAccessCode,
                      UserId userId,
                      CarId carId,
                      TimePeriod timePeriod) {
    this.campusAccessCode = campusAccessCode;
    this.userId = userId;
    this.carId = carId;
    this.timePeriod = timePeriod;
  }

  public UserId getUserId() {
    return userId;
  }

  public CarId getCarId() {
    return carId;
  }

  public TimePeriod getTimePeriod() {
    return timePeriod;
  }

  public CampusAccessCode getCampusAccessCode() {
    return campusAccessCode;
  }

  public boolean isAccessGranted(LocalDateTime dateOfAccess) {
    return timePeriod.include(dateOfAccess);
  }

  public void associatePass(PassCode passCode, TimePeriod passTimePeriod) {
    if (associatedPassCode != null) {
      throw new PassAlreadyAssociatedException("This user already has a pass for this date.");
    }
    if (!passTimePeriod.includedIn(timePeriod)) {
      throw new PassSaleNotAcceptedByAccessException(
              "This user does not have campus access for the dates covered by this pass."
      );
    }

    associatedPassCode = passCode;
  }

  public PassCode getAssociatedPassCode() {
    return associatedPassCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CampusAccess that = (CampusAccess) o;
    return campusAccessCode.equals(that.campusAccessCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campusAccessCode);
  }
}
