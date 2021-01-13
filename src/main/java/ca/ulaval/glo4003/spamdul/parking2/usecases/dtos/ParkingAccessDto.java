package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import java.time.LocalDateTime;

public class ParkingAccessDto {

  public LocalDateTime accessDateTime;
  public ParkingZone parkingZone;
  public LicensePlate licensePlate;
  public PermitNumber permitNumber;
}
