package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.exceptions;

public class InvalidCarTypeArgumentException extends InvalidCarArgumentException {

  public InvalidCarTypeArgumentException() {
    super("The car type must be one of gourmande, economique, hybride_economique, super_economique or sans_pollution");
  }
}
