package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import java.time.LocalDate;

public class ParkingUserFactory {

  private final ParkingUserIdFactory parkingUserIdFactory;

  public ParkingUserFactory(ParkingUserIdFactory parkingUserIdFactory) {
    this.parkingUserIdFactory = parkingUserIdFactory;
  }

  public ParkingUser create(String name, Sex sex, LocalDate birthDate) {
    return new ParkingUser(parkingUserIdFactory.create(), name, sex, birthDate);
  }
}
