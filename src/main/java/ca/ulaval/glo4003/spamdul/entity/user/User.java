package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccess;
import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
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
  private Car car;
  private CampusAccess campusAccess;
  private RechargULCard rechargULCard;
  private Map<InfractionId, Infraction> infractions;

  public User(UserId userId, String name, Gender gender, LocalDate birthDate, Car car) {
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
    this.userId = userId;
    this.car = car;
    this.infractions = new HashMap<>();
  }

  public void associate(Pass pass) {
    this.campusAccess.associatePass(pass);
  }

  public void associate(Infraction infraction) {
    infractions.put(infraction.getInfractionId(), infraction);
  }

  public void associate(RechargULCard rechargULCard) {
    this.rechargULCard = rechargULCard;
  }

  public void associate(CampusAccess campusAccess) {
    this.campusAccess = campusAccess;
  }

  public void associate(Car car) {
    this.car = car;
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

  public int getAge(LocalDate todaysDate) {
    return Period.between(birthDate, todaysDate).getYears();
  }

  public Pass getPass() {
    return campusAccess.getAssociatedPass();
  }

  public UserId getUserId() {
    return userId;
  }

  public boolean doesOwn(LicensePlate licensePlate) {
    return car.getLicensePlate().equals(licensePlate);
  }

  public boolean doesOwn(PassCode passCode) {
    return campusAccess.getAssociatedPass() != null && campusAccess.getAssociatedPass().getPassCode().equals(passCode);
  }

  public boolean doesOwn(RechargULCardId rechargULCardId) {
    return rechargULCard != null && rechargULCard.getId().equals(rechargULCardId);
  }

  public boolean doesOwn(CampusAccessCode campusAccessCode) {
    return campusAccess != null && campusAccess.getCampusAccessCode().equals(campusAccessCode);
  }

  public Car getCar() {
    return car;
  }

  public boolean isAccessGrantedToCampus(LocalDateTime dateTime) {
    if (campusAccess == null) {
      return false;
    } else {
      return campusAccess.grantAccess(dateTime);
    }
  }

  public ParkingZone getParkingZone() {
    return campusAccess.getParkingZone();
  }

  public List<Infraction> getAllInfractions() {
    return new ArrayList<>(infractions.values());
  }

  public Amount pay(InfractionId infractionId) {
    return infractions.get(infractionId).pay();
  }

  public RechargULCard addRechargUlCredits(Amount amount) {
    //TODO a tester
    rechargULCard.addCredits(amount);

    return rechargULCard;
  }

  public RechargULCard getRechargUlCard() {
    return rechargULCard;
  }

  public boolean hasInfractionWith(InfractionId infractionId) {
    Infraction infraction = infractions.get(infractionId);

    return infraction != null;
  }
}