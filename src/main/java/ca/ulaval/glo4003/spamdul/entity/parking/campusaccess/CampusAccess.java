package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

public class CampusAccess {

  private final CampusAccessCode campusAccessCode;
  protected TimePeriod timePeriod;
  private CarParkingPass associatedParkingPass;


  public CampusAccess(CampusAccessCode campusAccessCode,
                      TimePeriod timePeriod) {
    this.campusAccessCode = campusAccessCode;
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
      throw new PassAlreadyAssociatedException();
    }
    if (!carParkingPass.getTimePeriod().includedIn(timePeriod)) {
      throw new PassNotAcceptedByAccessException();
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

    return ParkingZone.FREE;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CampusAccess that = (CampusAccess) o;
    return Objects.equals(campusAccessCode, that.campusAccessCode)
        && Objects.equals(timePeriod, that.timePeriod);
  }

  public int hashCode() {
    return Objects.hash(campusAccessCode, timePeriod);
  }

  public boolean canParkInZone(ParkingZone parkingZone) {
    return associatedParkingPass.isAValidParkingZone(parkingZone);
  }

  public boolean hasParkingPassBoundingInstant(LocalDateTime now) {
    return associatedParkingPass.doesBoundInstant(now);
  }

  public boolean hasParkingRightOnThisDayOfWeek(DayOfWeek dayOfWeek) {
    return associatedParkingPass.isValidOnThisDayOfWeek(dayOfWeek);
  }
}
