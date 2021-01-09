package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;

public class CarCreationDto {

  public String brand;
  public String model;
  public int year;
  public CarType type;
  public LicensePlate licensePlate;
}
