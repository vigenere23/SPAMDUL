package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.LocalDateTime;

public class CampusAccess {

  private final CampusAccessCode campusAccessCode;
  private final UserId userId;
  private final CarId carId;
  private final PeriodType periodType;
  private final TimePeriod timePeriod;
  private PassCode associatedPassCode;

  public CampusAccess(CampusAccessCode campusAccessCode,
                      UserId userId,
                      CarId carId,
                      PeriodType periodType,
                      TimePeriod timePeriod) {
    this.campusAccessCode = campusAccessCode;
    this.userId = userId;
    this.carId = carId;
    this.periodType = periodType;
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

  public PassCode getAssociatedPassCode() {
    return associatedPassCode;
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
