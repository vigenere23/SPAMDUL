package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.user.car.Car;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserAlreadyHasACampusAccess;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserAlreadyHasARechargULCard;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserAlreadyHasThisInfraction;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

  private final String name;
  private final Gender gender;
  private final LocalDate birthDate;
  private final UserId userId;
  private final Map<InfractionId, Infraction> infractions;
  private Car car;
  private CampusAccess campusAccess;
  private RechargULCard rechargULCard;

  public User(UserId userId, String name, Gender gender, LocalDate birthDate, Car car) {
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
    this.userId = userId;
    this.car = car;
    this.infractions = new HashMap<>();
  }

  public Amount pay(InfractionId infractionId) {
    return infractions.get(infractionId).pay();
  }

  public RechargULCard addRechargULCredits(Amount amount) {
    rechargULCard.addCredits(amount);

    return rechargULCard;
  }

  public void verifyEnoughCreditsForCharging() {
    rechargULCard.verifyEnoughCreditsForCharging();
  }

  public void payForCharging(Amount amount) {
    rechargULCard.debit(amount);
  }

  public boolean isAccessGrantedToCampus(LocalDateTime dateTime) {
    if (campusAccess == null) {
      return false;
    } else {
      return campusAccess.grantAccess(dateTime);
    }
  }

  public void associate(Pass pass) {
    this.campusAccess.associatePass(pass);
  }

  public void associate(Infraction infraction) {
    if (infractions.get(infraction.getInfractionId()) != null) {
      throw new UserAlreadyHasThisInfraction();
    }
    infractions.put(infraction.getInfractionId(), infraction);
  }

  public void associate(RechargULCard rechargULCard) {
    if (this.rechargULCard != null) {
      throw new UserAlreadyHasARechargULCard();
    }
    this.rechargULCard = rechargULCard;
  }

  public void associate(CampusAccess campusAccess) {
    if (this.campusAccess != null) {
      throw new UserAlreadyHasACampusAccess();
    }
    this.campusAccess = campusAccess;
  }

  public void associate(Car car) {
    this.car = car;
  }


  public boolean doesOwn(LicensePlate licensePlate) {
    return car.getLicensePlate().equals(licensePlate);
  }

  public boolean doesOwn(PassCode passCode) {
    return campusAccess != null && campusAccess.getAssociatedPass() != null && campusAccess.getAssociatedPass()
                                                                                           .getPassCode()
                                                                                           .equals(passCode);
  }

  public boolean doesOwn(RechargULCardId rechargULCardId) {
    return rechargULCard != null && rechargULCard.getId().equals(rechargULCardId);
  }

  public boolean doesOwn(CampusAccessCode campusAccessCode) {
    return campusAccess != null && campusAccess.getCampusAccessCode().equals(campusAccessCode);
  }

  public boolean hasInfractionWith(InfractionId infractionId) {
    Infraction infraction = infractions.get(infractionId);

    return infraction != null;
  }

  public String getName() {
    return name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public Gender getGender() {
    return gender;
  }

  public int getAge(LocalDate todayDate) {
    return Period.between(birthDate, todayDate).getYears();
  }

  public Pass getPass() {
    return campusAccess.getAssociatedPass();
  }

  public UserId getId() {
    return userId;
  }

  public ParkingZone getParkingZone() {
    return campusAccess.getParkingZone();
  }

  public List<Infraction> getAllInfractions() {
    return new ArrayList<>(infractions.values());
  }

  public Car getCar() {
    return car;
  }

  public RechargULCard getRechargULCard() {
    return rechargULCard;
  }

  public boolean canParkInZone(ParkingZone parkingZone) {
    return campusAccess.canParkInZone(parkingZone);
  }

  public boolean hasParkingPassBoundingInstant(LocalDateTime now) {
    return campusAccess.hasParkingPassBoundingInstant(now);
  }

  public boolean canParkOnThisDayOfWeek(DayOfWeek dayOfWeek) {
    return campusAccess.hasParkingRightOnThisDayOfWeek(dayOfWeek);
  }
}
