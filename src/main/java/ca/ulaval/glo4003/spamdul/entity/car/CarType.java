package ca.ulaval.glo4003.spamdul.entity.car;

import java.text.Collator;

public enum CarType {

  GOURMANDE,
  ECONOMIQUE,
  HYBRIDE_ECONOMIQUE,
  SUPER_ECONOMIQUE,
  SANS_POLLUTION;

  private static final Collator collator = Collator.getInstance();

  public static CarType parse(String carTypeString) {
    collator.setStrength(Collator.NO_DECOMPOSITION);

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
