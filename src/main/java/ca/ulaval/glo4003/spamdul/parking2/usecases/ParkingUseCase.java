package ca.ulaval.glo4003.spamdul.parking2.usecases;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.InfractionCreator;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import java.time.LocalDateTime;

public class ParkingUseCase {

  private final ParkingUserRepository parkingUserRepository;
  private final CarFactory carFactory;
  private final InfractionCreator infractionCreator;

  public ParkingUseCase(ParkingUserRepository parkingUserRepository,
                        CarFactory carFactory,
                        InfractionCreator infractionCreator) {
    this.parkingUserRepository = parkingUserRepository;
    this.carFactory = carFactory;
    this.infractionCreator = infractionCreator;
  }

  /**
   * for entry (with license plate)
   */
  public void accessParking(LicensePlate licensePlate, LocalDateTime accessDateTime) {
    ParkingUser parkingUser = parkingUserRepository.findBy(licensePlate);
    parkingUser.validateAccess(licensePlate, accessDateTime);
  }

  /**
   * for entry (with permit number)
   */
  public void accessParking(PermitNumber permitNumber, LocalDateTime accessDateTime) {
    ParkingUser parkingUser = parkingUserRepository.findBy(permitNumber);
    parkingUser.validateAccess(permitNumber, accessDateTime);
  }

  /**
   * for entry (with permit number)
   */
  public void accessParking(PermitNumber permitNumber, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    ParkingUser parkingUser = parkingUserRepository.findBy(permitNumber);
    parkingUser.validateAccess(permitNumber, accessDateTime, parkingZone);
  }

  /**
   * for infractions
   */
  public void giveInfraction(PermitNumber permitNumber, LocalDateTime accessDateTime, ParkingZone parkingZone) {
    // do a try/catch to see if it's an invalid time, an invalid day, an invalid zone, if the permit does not exist, etc.
    // see infraction.json for more details

    ParkingUser parkingUser = parkingUserRepository.findBy(permitNumber);

    try {
      parkingUser.validateAccess(permitNumber, accessDateTime, parkingZone);
    } catch (InvalidAccessException exception) {
      addInfractionToUser(parkingUser, exception);
      parkingUserRepository.save(parkingUser);
    }
  }

  /**
   * for infractions
   */
  public void giveInfraction(PermitNumber permitNumber,
                             LicensePlate licensePlate,
                             LocalDateTime accessDateTime,
                             ParkingZone parkingZone) {
    // do a try/catch to see if it's an invalid time, an invalid day, an invalid zone, if the permit does not exist, etc.
    // see infraction.json for more details

    ParkingUser parkingUser = parkingUserRepository.findBy(permitNumber);

    try {
      parkingUser.validateAccess(permitNumber, licensePlate, accessDateTime, parkingZone);
    } catch (InvalidAccessException exception) {
      addInfractionToUser(parkingUser, exception);
      parkingUserRepository.save(parkingUser);
    }
  }

  public void addCarToUser(ParkingUserId parkingUserId/*, other car params*/) {

  }

  private void addInfractionToUser(ParkingUser parkingUser, InvalidAccessException exception) {
    Infraction infraction = infractionCreator.createInfraction(exception);
    parkingUser.addInfraction(infraction);
  }
}
