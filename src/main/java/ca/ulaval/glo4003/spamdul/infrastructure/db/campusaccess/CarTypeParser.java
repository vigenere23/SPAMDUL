package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import java.text.Collator;

public class CarTypeParser {

  public static CarType createCarType(String carTypeString, Collator collator) {
    if (collator.equals("gourmande", carTypeString.toLowerCase())) {
      return CarType.GOURMANDE;
    } else if (collator.equals("economique", carTypeString.toLowerCase())) {
      return CarType.ECONOMIQUE;
    } else if (collator.equals("hybride economique", carTypeString.toLowerCase())) {
      return CarType.HYBRIDE_ECONOMIQUE;
    } else if (collator.equals("super economique", carTypeString.toLowerCase())) {
      return CarType.SUPER_ECONOMIQUE;
    } else if (collator.equals("0 pollution", carTypeString.toLowerCase())) {
      return CarType.SANS_POLLUTION;
    } else {
      return null;
    }
  }
}
