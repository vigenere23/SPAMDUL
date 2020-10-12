package ca.ulaval.glo4003.spamdul.entity.car;

import java.text.Collator;

public enum CarType {

  GOURMANDE,
  ECONOMIQUE,
  HYBRIDE_ECONOMIQUE,
  SUPER_ECONOMIQUE,
  SANS_POLLUTION;

  public static CarType parse(String carTypeString, Collator collator) {
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
