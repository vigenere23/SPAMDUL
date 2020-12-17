package ca.ulaval.glo4003.spamdul.parking.usecases.infraction;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.LicensePlate;

public class InfractionPaymentDto {

  public InfractionId infractionId;
  public LicensePlate licensePlate;
}
